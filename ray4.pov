#include "colors.inc"

sphere {
     <-2-0 0, 0>, 1
     pigment { Red } 
}

  bicubic_patch {
    type 0
    flatness 0.1
    u_steps 4
    v_steps 4
    
    <-0.5, 0, 0>, <1, 0, 0>, <2, 0, 0>, <3, 0, 0>,
    <0, 1, 2>, <1, 5, 2>, <2, 5, 2>, <3, 1, 2>,
    <0, 2, 4>, <1, 6, 4>, <2, 6, 4>, <3, 2, 4>,
    <-1, 3, 6>, <1, 3, 6>, <2, 3, 6>, <4.5, 3, 6>
    
    pigment { Blue }
  }

light_source {
    <10,10, -10>
    color White
}     

camera {
    location <0, 0, -10>
    look_at <0, 0, 0>
}