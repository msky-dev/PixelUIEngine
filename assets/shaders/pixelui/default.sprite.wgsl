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
// Fragment Helpers
// =========================

fn colorTintAdd(color: vec4<f32>, modColor: vec4<f32>) -> vec4<f32> {
    var c = color;
    c.rgb = clamp(c.rgb + (modColor.rgb - vec3<f32>(0.5)), vec3<f32>(0.0), vec3<f32>(1.0));
    c.a = c.a * modColor.a;
    return c;
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

    let texColor = textureSample(u_texture, u_sampler, input.v_texCoord);

    return colorTintAdd(texColor, input.v_color);
}