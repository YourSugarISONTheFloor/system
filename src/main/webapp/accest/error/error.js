function randomNum() {
    return Math.floor(Math.random() * 9) + 1;
}

var loop1, loop2, loop3, time = 30, i = 0, number, num1, num2, num3;

function getNum(num_1, num_2, num_3) {
    num1 = num_1;
    num2 = num_2;
    num3 = num_3;
}

loop3 = setInterval(function () {
    if (i > 40) {
        clearInterval(loop3);
        document.querySelector('.thirdDigit').textContent = num1;
    } else {
        document.querySelector('.thirdDigit').textContent = randomNum();
        i++;
    }
}, time);
loop2 = setInterval(function () {
    if (i > 80) {
        clearInterval(loop2);
        document.querySelector('.secondDigit').textContent = num2;
    } else {
        document.querySelector('.secondDigit').textContent = randomNum();
        i++;
    }
}, time);
loop1 = setInterval(function () {
    if (i > 100) {
        clearInterval(loop1);
        document.querySelector('.firstDigit').textContent = num3;
    } else {
        document.querySelector('.firstDigit').textContent = randomNum();
        i++;
    }
}, time);