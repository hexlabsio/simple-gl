// Reference mocha-typescript's global definitions:
/// <reference path="../node_modules/mocha-typescript/globals.d.ts" />
import { suite, test } from "mocha-typescript";
import { expect } from 'chai';
import { Vector2D } from "../src/vector";

@suite
class VectorTest {

    @test translate() {
        expect(new Vector2D(0,0).translate(new Vector2D(10,25))).to.deep.equal(new Vector2D(10,25))
        expect(new Vector2D(-95,-3).translate(new Vector2D(5,10))).to.deep.equal(new Vector2D(-90,7))
    }

    @test subtract() {
        expect(new Vector2D(10,25).subtract(new Vector2D(5,50))).to.deep.equal(new Vector2D(5,-25))
    }

    @test dotProduct() {
        expect(new Vector2D(10,25).dot(new Vector2D(3,-2))).to.equal(-20)
    }

    @test scale() {
        expect(new Vector2D(3,2).scale(30)).to.deep.equal(new Vector2D(90,60))
    }

    @test scaleV() {
        expect(new Vector2D(-12,4).scaleV(new Vector2D(9,8))).to.deep.equal(new Vector2D(-108,32))
    }
}