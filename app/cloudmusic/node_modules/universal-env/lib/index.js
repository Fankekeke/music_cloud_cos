"use strict";

Object.defineProperty(exports, "__esModule", {
  value: true
});
exports.default = exports.isReactNative = exports.isWeex = exports.isNode = exports.isWeb = void 0;

function _typeof(obj) { if (typeof Symbol === "function" && typeof Symbol.iterator === "symbol") { _typeof = function _typeof(obj) { return typeof obj; }; } else { _typeof = function _typeof(obj) { return obj && typeof Symbol === "function" && obj.constructor === Symbol && obj !== Symbol.prototype ? "symbol" : typeof obj; }; } return _typeof(obj); }

// https://www.w3.org/TR/html5/webappapis.html#dom-navigator-appcodename
var isWeb = (typeof navigator === "undefined" ? "undefined" : _typeof(navigator)) === 'object' && (navigator.appCodeName === 'Mozilla' || navigator.product === 'Gecko');
exports.isWeb = isWeb;
var isNode = typeof process !== 'undefined' && !!(process.versions && process.versions.node);
exports.isNode = isNode;
var isWeex = typeof callNative === 'function' || (typeof WXEnvironment === "undefined" ? "undefined" : _typeof(WXEnvironment)) === 'object' && WXEnvironment.platform !== 'Web';
exports.isWeex = isWeex;
var isReactNative = typeof __fbBatchedBridgeConfig !== 'undefined';
exports.isReactNative = isReactNative;
exports["default"] = module.exports;
var _default = module.exports;
exports.default = _default;