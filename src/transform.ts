import { Matrix3 } from "./matrix";
import { Vector2D } from "..";

export class Transform2D{
    _scale: Vector2D = new Vector2D(1,1)
    _translation: Vector2D = new Vector2D(1,1)
    translate(translation: Vector2D): Transform2D{
        this._translation = this._translation.translate(translation)
        return this
    }
    scale(scale: Vector2D): Transform2D{
        this._scale = this._scale.scaleV(scale)
        return this
    }
    transform(): Matrix3{
        return Matrix3.identity().scale(this._scale).translate(this._translation)
    }
}