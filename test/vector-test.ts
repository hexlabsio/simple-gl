// Reference mocha-typescript's global definitions:
/// <reference path="../node_modules/mocha-typescript/globals.d.ts" />
import { suite, test } from "mocha-typescript";
import { expect } from 'chai';
import { Vector2D, Vector3D } from "../src/vector";

@suite
class VectorTest {

    @test translate2D() {
        expect(new Vector2D(0,0).translate(new Vector2D(10,25))).to.deep.equal(new Vector2D(10,25))
        expect(new Vector2D(-95,-3).translate(new Vector2D(5,10))).to.deep.equal(new Vector2D(-90,7))
    }

    @test subtract2D() {
        expect(new Vector2D(10,25).subtract(new Vector2D(5,50))).to.deep.equal(new Vector2D(5,-25))
    }

    @test dotProduct2D() {
        expect(new Vector2D(10,25).dot(new Vector2D(3,-2))).to.equal(-20)
    }

    @test scale2D() {
        expect(new Vector2D(3,2).scale(30)).to.deep.equal(new Vector2D(90,60))
    }

    @test scaleV2D() {
        expect(new Vector2D(-12,4).scaleV(new Vector2D(9,8))).to.deep.equal(new Vector2D(-108,32))
    }

    @test translate3D() {
        expect(new Vector3D(0,0,0).translate(new Vector3D(10,-3, 12))).to.deep.equal(new Vector3D(10,-3, 12))
        expect(new Vector3D(1,-90,100).translate(new Vector3D(10,-3, 12))).to.deep.equal(new Vector3D(11,-93, 112))
    }

    @test subtract3D() {
        expect(new Vector3D(100,200,-300).subtract(new Vector3D(1,2,3))).to.deep.equal(new Vector3D(99,198,-303))
    }

    @test dotProduct3D() {
        expect(new Vector3D(0, 1, 2).dot(new Vector3D(1, 2, -3))).to.equal(-4)
    }

    @test scale3D() {
        expect(new Vector3D(3,2, 1).scale(30)).to.deep.equal(new Vector3D(90,60, 30))
    }

    @test scaleV3D() {
        expect(new Vector3D(-12,4,3).scaleV(new Vector3D(9,8,7))).to.deep.equal(new Vector3D(-108,32,21))
    }
}