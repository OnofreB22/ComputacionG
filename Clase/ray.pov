#include "colors.inc"

plane {
    y, -2
    pigment { checker Pink, White }
}

sphere {
    <-4, 0, 0>, 1
    pigment { Blue }
    finish {
        ambient 0.05  // Muy poco brillo ambiente
        diffuse 0.4   // Iluminación difusa suave
        phong 0.3     // Brillo especular tenue
        phong_size 20
        specular 0.5
    }
}

sphere {
    <0, 0, 0>, 1
    pigment { Red }
    finish {
        ambient 0.1
        diffuse 0.5
        phong 0.4
        phong_size 30
        specular 0.2
    }
}

sphere {
    <4, 0, 0>, 1
    pigment { Green }
    finish {
        ambient 0.08
        diffuse 0.5
        phong 0.2     // Reducción del brillo especular
        phong_size 15
        specular 0.6
    }
}

light_source {
    <20, 30, -15>
    color rgb <0.8, 0.8, 0.8>  // Luz suave, menos intensa
}

camera {
    location <0, 2, -12>
    look_at <0, 0, 0>
}
