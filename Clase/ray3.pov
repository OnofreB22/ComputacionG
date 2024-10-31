#include "colors.inc"

plane {
    y, -2
    pigment { checker White, Black }
}

sphere {
    <-5, 0, 0>, 1
    pigment { Orange }
    finish {
        ambient 0.15
        diffuse 0.7
        phong 0.5    // Brillo especular moderado
        phong_size 10 // Manchas de luz más difusas
    }
}

sphere {
    <0, 0, 0>, 1
    pigment { Blue }
    finish {
        ambient 0.1
        diffuse 0.75
        phong 0.6
        phong_size 25
    }
}

sphere {
    <5, 0, 0>, 1
    pigment { Cyan }
    finish {
        ambient 0.1
        diffuse 0.8
        phong 0.6
        phong_size 40  // Brillo especular más difuso
    }
}

light_source {
    <10, 10, -5>
    color rgb <1, 0.8, 0.7> // Luz cálida
}

camera {
    location <0, 0, -12>
    look_at <0, 0, 0>
}
