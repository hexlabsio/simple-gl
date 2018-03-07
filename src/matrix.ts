import { Vector2D, Vector3D } from "./vector";

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
            (T.b*T.f-T.e*T.c), -(T.a*T.f-T.d*T.c), (T.a*T.e-T.d*T.b)
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

export class Matrix4{
    a: number; b: number; c: number; d: number;
    e: number; f: number; g: number; h: number;
    i: number; j: number; k: number; l: number;
    m: number; n: number; o: number; p: number;
    constructor(
        a: number, b: number, c: number, d: number,
        e: number, f: number, g: number, h: number,
        i: number, j: number, k: number, l: number,
        m: number, n: number, o: number, p: number
    ){ this.a = a; this.b = b; this.c = c; this.d = d; this.e = e; this.f = f; this.g = g; this.h = h; this.i = i; this.j = j; this.k = k; this.l = l; this.m = m; this.n = n; this.o = o; this.p = p; }
    
    multiply(b: Matrix4): Matrix4{ return Matrix4.multiply(this, b) }
    translate(translation: Vector3D){ return this.multiply(Matrix4.translation(translation)) }
    scale(scale: Vector3D){ return this.multiply(Matrix4.scale(scale)) }

    transform(vector: Vector3D): Vector3D{
        return new Vector3D(
            this.a * vector.x + this.b * vector.y + this.c * vector.z + this.d,
            this.e * vector.x + this.f * vector.y + this.g * vector.z + this.h,
            this.i * vector.x + this.j * vector.y + this.k * vector.z + this.l
        )
    }

    floats(): number[]{
        return [
            this.a, this.b, this.c, this.d,
            this.e, this.f, this.g, this.h,
            this.i, this.j, this.k, this.l,
            this.m, this.n, this.o, this.p
        ]
    }

    transpose(): Matrix4{
        return new Matrix4(
            this.a, this.e, this.i, this.m,
            this.b, this.f, this.j, this.n,
            this.c, this.g, this.k, this.o,
            this.d, this.h, this.l, this.p
        )
    }

    static multiply(a: Matrix4, b: Matrix4): Matrix4{
        return new Matrix4(
            a.a*b.a + a.b*b.e + a.c*b.i + a.d*b.m, a.a*b.b + a.b*b.f + a.c*b.j + a.d*b.n, a.a*b.c + a.b*b.g + a.c*b.k + a.d*b.o, a.a*b.d + a.b*b.h + a.c*b.l + a.d*b.p,
            a.e*b.a + a.f*b.e + a.g*b.i + a.h*b.m, a.e*b.b + a.f*b.f + a.g*b.j + a.h*b.n, a.e*b.c + a.f*b.g + a.g*b.k + a.h*b.o, a.e*b.d + a.f*b.h + a.g*b.l + a.h*b.p,
            a.i*b.a + a.j*b.e + a.k*b.i + a.l*b.m, a.i*b.b + a.j*b.f + a.k*b.j + a.l*b.n, a.i*b.c + a.j*b.g + a.k*b.k + a.l*b.o, a.i*b.d + a.j*b.h + a.k*b.l + a.l*b.p,
            a.m*b.a + a.n*b.e + a.o*b.i + a.p*b.m, a.m*b.b + a.n*b.f + a.o*b.j + a.p*b.n, a.m*b.c + a.n*b.g + a.o*b.k + a.p*b.o, a.m*b.d + a.n*b.h + a.o*b.l + a.p*b.p
        )
    }

    static translation(translation: Vector3D): Matrix4{
        return new Matrix4(
            1,0,0,translation.x,
            0,1,0,translation.y,
            0,0,1, translation.z,
            0,0,0,1
        )
    }
    static scale(scale: Vector3D): Matrix4{
        return new Matrix4(
            scale.x,0,0,0,
            0,scale.y,0,0,
            0,0,scale.z,0,
            0,0,0,1
        )
    }
    static identity(): Matrix4{
        return new Matrix4(
            1,0,0,0,
            0,1,0,0,
            0,0,1,0,
            0,0,0,1
        )
    }
    static aspect(fieldOfViewInRadians: number, width: number, height: number): Matrix4{
        let f = Math.tan(Math.PI * 0.5 - 0.5 * fieldOfViewInRadians);
        let aspect = width/height
        return new Matrix4(
            -f/aspect, 0, 0,0,
            0, f, 0,0,
            0, 0, 1,0,
            0,0,0,1
        )
    }
}