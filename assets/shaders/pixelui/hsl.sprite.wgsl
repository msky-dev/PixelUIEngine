const FLOAT_CORRECTION: f32 = 255.0 / 254.0;

// =========================
// Uniforms & Bindings
// =========================

@group(0) @binding(0)
var<uniform> u_projTrans : mat4x4<f32>;

@group(0) @binding(1)
var u_texture : texture_2d<f32>;

@group(0) @binding(2)
var u_sampler : sampler;

@group(0) @binding(3)
var<uniform> u_textureSize : vec2<f32>;


// =========================
// Vertex Stage
// =========================

struct VertexInput {
    @location(0) a_position : vec4<f32>,
    @location(1) a_color    : vec4<f32>,
    @location(2) a_tweak    : vec4<f32>,
    @location(3) a_texCoord : vec2<f32>,
};

struct VertexOutput {
    @builtin(position) position : vec4<f32>,
    @location(0) v_color    : vec4<f32>,
    @location(1) v_tweak    : vec4<f32>,
    @location(2) v_texCoord : vec2<f32>,
};

@vertex
fn vs_main(input: VertexInput) -> VertexOutput {
    var out: VertexOutput;

    out.v_color    = input.a_color * FLOAT_CORRECTION;
    out.v_tweak    = input.a_tweak * FLOAT_CORRECTION;
    out.v_texCoord = input.a_texCoord;
    out.position   = u_projTrans * input.a_position;

    return out;
}


// =========================
// Fragment Stage Helpers
// =========================

fn colorTintAdd(color: vec4<f32>, modColor: vec4<f32>) -> vec4<f32> {
    var c = color;
    c.rgb = clamp(c.rgb + (modColor.rgb - vec3<f32>(0.5)), vec3<f32>(0.0), vec3<f32>(1.0));
    c.a = c.a * modColor.a;
    return c;
}

fn hsl2rgb(c: vec4<f32>) -> vec4<f32> {
    let eps: f32 = 1.0e-10;
    let K: vec4<f32> = vec4<f32>(1.0, 2.0/3.0, 1.0/3.0, 3.0);

    let p = abs(fract(c.x + K.xyz) * 6.0 - K.www);
    let v = c.z + c.y * min(c.z, 1.0 - c.z);

    let rgb = v * mix(
        K.xxx,
        clamp(p - K.xxx, vec3<f32>(0.0), vec3<f32>(1.0)),
        vec3<f32>(2.0 * (1.0 - c.z / (v + eps)))
    );

    return vec4<f32>(rgb, c.w);
}

fn rgb2hsl(c: vec4<f32>) -> vec4<f32> {
    let eps: f32 = 1.0e-10;
    let J: vec4<f32> = vec4<f32>(0.0, -1.0/3.0, 2.0/3.0, -1.0);

    let p = mix(
        vec4<f32>(c.b, c.g, J.w, J.z),
        vec4<f32>(c.g, c.b, J.x, J.y),
        step(c.b, c.g)
    );

    let q = mix(
        vec4<f32>(p.x, p.y, p.w, c.r),
        vec4<f32>(c.r, p.y, p.z, p.x),
        step(p.x, c.r)
    );

    let d = q.x - min(q.w, q.y);
    let l = q.x * (1.0 - 0.5 * d / (q.x + eps));

    return vec4<f32>(
        abs(q.z + (q.w - q.y) / (6.0 * d + eps)),
        (q.x - l) / (min(l, 1.0 - l) + eps),
        l,
        c.a
    );
}


// =========================
// Fragment Stage
// =========================

struct FragmentInput {
    @location(0) v_color    : vec4<f32>,
    @location(1) v_tweak    : vec4<f32>,
    @location(2) v_texCoord : vec2<f32>,
};

@fragment
fn fs_main(input: FragmentInput) -> @location(0) vec4<f32> {

    var color = textureSample(u_texture, u_sampler, input.v_texCoord);
    color = colorTintAdd(color, input.v_color);

    var hsl = rgb2hsl(color);

    hsl.x = fract(hsl.x + (input.v_tweak.x - 0.5));
    hsl.y = clamp(hsl.y + ((input.v_tweak.y - 0.5) * 2.0), 0.0, 1.0);
    hsl.z = clamp(hsl.z + ((input.v_tweak.z - 0.5) * 2.0), 0.0, 1.0);

    return hsl2rgb(hsl);
}