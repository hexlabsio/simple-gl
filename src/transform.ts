import { Matrix3, Matrix4 } from "./matrix";
import { Vector2D, Vector3D } from "./vector";

export class Transform2D{
    _scale: Vector2D = new Vector2D(1,1)
    _translation: Vector2D = new Vector2D(0,0)
    changed: Boolean = false
    cached: Matrix3

    translate(translation: Vector2D): Transform2D{
        this.changed = true
        this._translation = this._translation.translate(translation)
        return this
    }
    scale(scale: Vector2D): Transform2D{
        this.changed = true
        this._scale = this._scale.scaleV(scale)
        return this
    }
    transform(): Matrix3{
        if(!this.cached || this.changed){
            this.cached = Matrix3.identity().scale(this._scale).translate(this._translation)
        }
        return this.cached
    }
}
export class Transform3D{
    _scale: Vector3D = new Vector3D(1,1,1)
    _translation: Vector3D = new Vector3D(0,0,0)
    changed: Boolean = false
    cached: Matrix4

    translate(translation: Vector3D): Transform3D{
        this.changed = true
        this._translation = this._translation.translate(translation)
        return this
    }
    scale(scale: Vector3D): Transform3D{
        this.changed = true
        this._scale = this._scale.scaleV(scale)
        return this
    }
    transform(): Matrix4{
        if(!this.cached || this.changed){
            this.cached = Matrix4.identity().scale(this._scale).translate(this._translation)
        }
        return this.cached
    }
}