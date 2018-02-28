import { Vector2D } from "./vector";

export class Matrix3{
    a: number; b: number; c: number
    d: number; e: number; f: number
    g: number; h: number; i: number
    constructor(
        a: number, b: number, c: number,
        d: number, e: number, f: number,
        g: number, h: number, i: number
    ){ this.a = a; this.b = b; this.c = c; this.d = d; this.e = e; this.f = f; this.g = g; this.h = h; this.i = i }
    
    multiply(b: Matrix3): Matrix3{ return Matrix3.multiply(this, b) }
    translate(translation: Vector2D){ return this.multiply(Matrix3.translation(translation)) }
    scale(scale: Vector2D){ return this.multiply(Matrix3.scale(scale)) }

    transform(vector: Vector2D): Vector2D{
        return new Vector2D(this.a*vector.x + this.b * vector.y + this.c, this.d*vector.x + this.e*vector.y + this.f)
    }

    static multiply(a: Matrix3, b: Matrix3): Matrix3{
        return new Matrix3(
            a.a*b.a+a.b*b.d+a.c*b.g, a.a*b.b+a.b*b.e+a.c*b.h, a.a*b.c+a.b*b.f+a.c*b.i,
            a.d*b.a+a.e*b.d+a.f*b.g, a.d*b.b+a.e*b.e+a.f*b.h, a.d*b.c+a.e*b.f+a.f*b.i,
            a.g*b.a+a.h*b.d+a.i*b.g, a.g*b.b+a.h*b.e+a.i*b.h, a.g*b.c+a.h*b.f+a.i*b.i
        )
    }

    static translation(translation: Vector2D): Matrix3{
        return new Matrix3(
            1,0,translation.x,
            0,1,translation.y,
            0,0,1
        )
    }
    static scale(scale: Vector2D): Matrix3{
        return new Matrix3(
            scale.x,0,0,
            0,scale.y,0,
            0,0,1
        )
    }
    static identity(): Matrix3{
        return new Matrix3(
            1,0,0,
            0,1,0,
            0,0,1
        )
    }
}
//|a b c|\\
//|d e f|\\
//|g h i|\\