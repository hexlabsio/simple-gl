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

    matrix4Floats(): number[]{
        return [
            this.a, this.b, this.c, 0,
            this.d, this.e, this.f, 0,
            this.g, this.h, this.i, 0,
            0,0,0,1
        ]
    }

    determinant(): number{
        return this.a*(this.e*this.i-this.h*this.f)-this.b*(this.d*this.i-this.g*this.f)+this.c*(this.d*this.h-this.g*this.e)
    }

    transpose(): Matrix3{
        return new Matrix3(
            this.a, this.d, this.g,
            this.b, this.e, this.h,
            this.c, this.f, this.i
        )
    }

    adjugate(): Matrix3{
        let T = this.transpose()
        return new Matrix3(
            (T.e*T.i-T.h*T.f), -(T.d*T.i-T.g*T.f), (T.d*T.h-T.g*T.e),
            -(T.b*T.i-T.h*T.c), (T.a*T.i-T.g*T.c), -(T.a*T.h-T.g*T.b),
            (T.b*T.i-T.h*T.c), -(T.a*T.f-T.d*T.c), (T.a*T.e-T.d*T.b)
        )
    }

    inverse(): Matrix3{
        let determinant = this.determinant()
        if(determinant != 0){
            let adjugate = this.adjugate()
            return new Matrix3(
                adjugate.a/determinant, adjugate.b/determinant, adjugate.c/determinant,
                adjugate.d/determinant, adjugate.e/determinant, adjugate.f/determinant,
                adjugate.g/determinant, adjugate.h/determinant, adjugate.i/determinant
            )
        }
        else return Matrix3.identity()
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
    static aspect(fieldOfViewInRadians: number, width: number, height: number): Matrix3{
        let f = Math.tan(Math.PI * 0.5 - 0.5 * fieldOfViewInRadians);
        let aspect = width/height
        return new Matrix3(
            -f/aspect, 0, 0,
            0, f, 0,
            0, 0, 1
        )
    }
}
//|a b c|\\
//|d e f|\\
//|g h i|\\