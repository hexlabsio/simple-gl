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

    @test transpose() {
        let M = new Matrix3(
            1,2,3,
            4,5,6,
            7,8,9
        )
        expect(M.transpose()).to.deep.equal(
            new Matrix3(
                1,4,7,
                2,5,8,
                3,6,9
            )
        )
    }

    @test determinant() {
        let M = new Matrix3(
            1,2,3,
            0,1,4,
            5,6,0
        )
        expect(M.determinant()).to.equal(1)
    }

    @test adjugate() {
        let M = new Matrix3(
            1,2,3,
            0,1,4,
            5,6,0
        )
        expect(M.adjugate()).to.deep.equal(
            new Matrix3(
                -24, 18, 5,
                20,-15,-4,
                -5,4,1
            )
        )
    }

    @test inverse() {
        let M = new Matrix3(
            1,2,3,
            0,1,4,
            5,6,0
        )
        let MInverse = new Matrix3(
            -24, 18, 5,
            20,-15,-4,
            -5,4,1
        )
        expect(M.inverse()).to.deep.equal(MInverse)
        expect(MInverse.multiply(M)).to.deep.equal(Matrix3.identity())
    }

}