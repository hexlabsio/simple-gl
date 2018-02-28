import { suite, test } from "mocha-typescript";
import { expect } from 'chai';
import { Vector2D } from "../src/vector";
import { Matrix3 } from "../src/matrix";

@suite
class MatrixTest {

    testMatrix = new Matrix3(
        1,2,3,
        4,5,6,
        7,8,9
    )

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
        expect(this.testMatrix.translate(new Vector2D(10,-5))).to.deep.equal(
            new Matrix3(
                1,2,3,
                4,5,21,
                7,8,39
            )
        )
    }

    @test scale() {
        expect(this.testMatrix.scale(new Vector2D(10,-5))).to.deep.equal(
            new Matrix3(
                10,-10,3,
                40,-25,6,
                70,-40,9
            )
        )
    }

    @test transform() {
        let transformationMatrix = Matrix3.identity().scale(new Vector2D(0.5,2)).translate(new Vector2D(-2,4))
        let vector = new Vector2D(5,3)
        expect(transformationMatrix.transform(vector)).to.deep.equal(new Vector2D(1.5,14))
    }

}