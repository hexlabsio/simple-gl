import { suite, test } from "mocha-typescript";
import { expect } from 'chai';
import { Vector2D } from "../src/vector";
import { Matrix3 } from "../src/matrix";

@suite
class MatrixTest {


    @test identity() {
        expect(Matrix3.identity()).to.deep.equal(
            new Matrix3(
                1,0,0,
                0,1,0,
                0,0,1
            )
        )
    }

    @test translation() {
        expect(Matrix3.identity().translate(new Vector2D(10,-5)).translate(new Vector2D(6,3))).to.deep.equal(
            new Matrix3(
                1,0,16,
                0,1,-2,
                0,0,1
            )
        )
    }

    @test scale() {
        expect(Matrix3.identity().scale(new Vector2D(10,-5)).scale(new Vector2D(2,0.1))).to.deep.equal(
            new Matrix3(
                20,0,0,
                0,-0.5,0,
                0,0,1
            )
        )
    }

    @test transform() {
        let transformationMatrix = Matrix3.identity().scale(new Vector2D(0.5,2)).translate(new Vector2D(-2,4))
        let vector = new Vector2D(5,3)
        expect(transformationMatrix.transform(vector)).to.deep.equal(new Vector2D(1.5,14))
    }

}