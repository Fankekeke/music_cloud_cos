'use strict';

const Timer = require('../src/');

let block = document.querySelector('#block');

const bezierArgs = [0.1,0.9,.5,.9];

let timer = new Timer({
  duration: 500,
  bezierArgs,
  //easing: 'easeOutSine',
  onStart: (e) => {
    console.log(e);
  },
  onRun: (e) => {
    block.style.webkitTransform = `translateX(${e.percent * 200}px)`;
  },
  onEnd: (e) => {
    console.log(e);
  }
});


timer.run();
