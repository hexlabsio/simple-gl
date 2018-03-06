export class Vector2D{
    x: number
    y: number

    constructor(x: number, y: number){
        this.x = x
        this.y = y
    }

    translate(b: Vector2D): Vector2D { return Vector2D.translate(this, b) }
    subtract(a: Vector2D): Vector2D { return Vector2D.difference(a, this) }
    dot(b: Vector2D): number { return Vector2D.dot(this, b) }
    scale(scale: number): Vector2D { return Vector2D.scale(this, scale) }
    scaleV(scale: Vector2D): Vector2D { return Vector2D.scaleV(this, scale) }

    length(): number{
        return Math.sqrt(this.x*this.x + this.y*this.y)
    }

    direction(length: number = this.length()): Vector2D{
        if(length == 0) return new Vector2D(0,0)
        return new Vector2D(this.x/length, this.y/length)
    }

    static translate(a: Vector2D, b: Vector2D): Vector2D{ 
        return new Vector2D(a.x + b.x, a.y + b.y)
    }
    static difference(a: Vector2D, b: Vector2D): Vector2D{ 
        return new Vector2D(b.x - a.x, b.y - a.y)
    }
    static dot(a: Vector2D, b: Vector2D): number{ 
        return a.x * b.x + a.y * b.y
    }
    static scale(a: Vector2D, scale: number): Vector2D{ 
        return new Vector2D(a.x * scale, a.y * scale)
    }
    static scaleV(a: Vector2D, scale: Vector2D): Vector2D{ 
        return new Vector2D(a.x * scale.x, a.y * scale.y)
    }
}

export class Vector3D{
    x: number
    y: number
    z: number

    constructor(x: number, y: number, z: number){
        this.x = x
        this.y = y
        this.z = z
    }

    translate(b: Vector3D): Vector3D { return Vector3D.translate(this, b) }
    subtract(a: Vector3D): Vector3D { return Vector3D.difference(a, this) }
    dot(b: Vector3D): number { return Vector3D.dot(this, b) }
    scale(scale: number): Vector3D { return Vector3D.scale(this, scale) }
    scaleV(scale: Vector3D): Vector3D { return Vector3D.scaleV(this, scale) }

    length(): number{
        return Math.sqrt(this.x*this.x + this.y*this.y + this.z*this.z)
    }

    direction(length: number = this.length()): Vector3D{
        if(length == 0) return new Vector3D(0,0,0)
        return new Vector3D(this.x/length, this.y/length, this.z/length)
    }

    static translate(a: Vector3D, b: Vector3D): Vector3D{ 
        return new Vector3D(a.x + b.x, a.y + b.y, a.z + b.z)
    }
    static difference(a: Vector3D, b: Vector3D): Vector3D{ 
        return new Vector3D(b.x - a.x, b.y - a.y, b.z - a.z)
    }
    static dot(a: Vector3D, b: Vector3D): number{ 
        return a.x * b.x + a.y * b.y + a.z * b.z
    }
    static scale(a: Vector3D, scale: number): Vector3D{ 
        return new Vector3D(a.x * scale, a.y * scale, a.z * scale)
    }
    static scaleV(a: Vector3D, scale: Vector3D): Vector3D{ 
        return new Vector3D(a.x * scale.x, a.y * scale.y, a.z * scale.z)
    }
}