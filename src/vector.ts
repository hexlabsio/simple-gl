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