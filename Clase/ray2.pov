#include "colors.inc"

plane {
    y, -2
    pigment { checker Black, White }
}

sphere {
    <-4, 0, 0>, 1
    pigment { Silver }
    finish {
        ambient 0.3   // Luz ambiental fuerte
        diffuse 0.8   // Alta reflexi�n de la luz difusa
        phong 0.9     // Brillo especular muy intenso
        phong_size 50
    }
}

sphere {
    <0, 0, 0>, 1
    pigment { Gold }
    finish {
        ambient 0.25
        diffuse 0.9
        phong 0.95    // Reflejo casi met�lico
        phong_size 70
    }
}

sphere {
    <4, 0, 0>, 1
    pigment { Copper }
    finish {
        ambient 0.2
        diffuse 0.85
        phong 0.85
        phong_size 80
    }
}

light_source {
    <10, 15, -5>
    color rgb Pink
}

camera {
    location <0, 1, -10>
    look_at <0, 0, 0>
}
