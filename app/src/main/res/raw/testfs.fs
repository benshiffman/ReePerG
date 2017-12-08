#version 400 core

in vec2 pass_textureCoords;

out vec4 out_Color;

uniform sampler2D textureSampler;

void main(void) {
    out_Color = vec4(0f, 1f, 0f, 1f);
	//out_Color = texture(textureSampler, pass_textureCoords);
}