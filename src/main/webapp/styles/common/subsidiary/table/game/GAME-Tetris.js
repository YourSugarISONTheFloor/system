var Tetris = function (options) {

	//方块区域

	this.e_playArea = $("#play_area");

	//开始按钮

	this.e_startBtn = $("#play_btn_start");

	//分数

	this.e_playScore = $("#play_score");

	//方向

	this.e_playDirection = $("#play_direction");

	//等级按钮

	this.e_levelBtn = $("#play_btn_level");

	//等级菜单

	this.e_levelMenu = $("#play_menu_level");

	//下一个方块的类型

	this.e_nextType = $("#play_nextType");

	//总的列数

	this.cellCol = 15;

	//总的行数

	this.cellRow = 24;

	//每个小方块的属性数组

	this.cellArr = [];

	//保存右上方下一个那里的小图形数组

	this.miniCellArr = [];

	//分数

	this.score = 0;

	//方向

	this.direction = "bottom";

	//定时器的ID

	this.timer = null;

	//定时器的时间数组

	this.interval = [600, 300, 100];

	//等级的分数数组

	this.levelScore = [10, 20, 40];

	//消除的行数多少而给的倍数

	this.doubleScore = [1, 4, 10, 20];

	//默认等级1

	this.level = 1;

	//游戏是否进行

	this.playing = false;

	//游戏暂停标记

	this.turning = false;

	//游戏结束标记

	this.death = false;

	//一开始方块列出现的位置

	this.offsetCol = Math.floor(this.cellCol / 2);

	//一开始方块行出现的位置

	this.offsetRow = -3;



	this.tetrisArr = [];

	/*

	    0,1,15,16

	    0,1,15,16

	 */

	this.tetrisArr[0] = [

		[0, 1, this.cellCol, this.cellCol + 1],

		[0, 1, this.cellCol, this.cellCol + 1]

	];

	/*

	    1,14,15,16

	    0,15,30,31

	    14,15,16,29

	    -1,0,15,30

	 */

	this.tetrisArr[1] = [

		[1, this.cellCol - 1, this.cellCol, this.cellCol + 1],

		[0, this.cellCol, this.cellCol * 2, this.cellCol * 2 + 1],

		[this.cellCol - 1, this.cellCol, this.cellCol + 1, this.cellCol * 2 - 1],

		[-1, 0, this.cellCol, this.cellCol * 2]

	];

	/*

	    -1,14,15,16

	    0,1,15,30

	    14,15,16,31

	    0,15,29,30

	 */

	this.tetrisArr[2] = [

		[-1, this.cellCol - 1, this.cellCol, this.cellCol + 1],

		[0, 1, this.cellCol, this.cellCol * 2],

		[this.cellCol - 1, this.cellCol, this.cellCol + 1, this.cellCol * 2 + 1],

		[0, this.cellCol, this.cellCol * 2 - 1, this.cellCol * 2]

	];

	/*

	    0,15,16,31

	    15,16,29,30

	 */

	this.tetrisArr[3] = [

		[0, this.cellCol, this.cellCol + 1, this.cellCol * 2 + 1],

		[this.cellCol, this.cellCol + 1, this.cellCol * 2 - 1, this.cellCol * 2]

	];

	/*

	    0,14,15,29

	    14,15,30,31

	 */

	this.tetrisArr[4] = [

		[0, this.cellCol - 1, this.cellCol, this.cellCol * 2 - 1],

		[this.cellCol - 1, this.cellCol, this.cellCol * 2, this.cellCol * 2 + 1]

	];

	/*

	    0,14,15,16

	    0,15,16,30

	    14,15,16,30

	    0,14,15,30

	 */

	this.tetrisArr[5] = [

		[0, this.cellCol - 1, this.cellCol, this.cellCol + 1],

		[0, this.cellCol, this.cellCol + 1, this.cellCol * 2],

		[this.cellCol - 1, this.cellCol, this.cellCol + 1, this.cellCol * 2],

		[0, this.cellCol - 1, this.cellCol, this.cellCol * 2]

	];

	/*

	    0,15,30,45

	    14,15,16,17

	 */

	this.tetrisArr[6] = [

		[0, this.cellCol, this.cellCol * 2, this.cellCol * 3],

		[this.cellCol - 1, this.cellCol, this.cellCol + 1, this.cellCol + 2]

	];

	this.tetrisType = [1, 1];

	this.tetrisType = [1, 0];

	this.tetrisTypeArr = [];



	this.preTetris = [];

	this.thisTetris = [];

	this.fullArr = [];



	this.start();

}

Tetris.prototype = {

	//开始入口函数

	start: function () {

		this.init();

		this.menu();

		this.control();

	},

	//选择设置

	setOptions: function (options) {

		this.score = options.score === 0 ? options.score

			: (options.score || this.score);

		this.level = options.level === 0 ? options.level

			: (options.level || this.level);

	},

	resetArea: function () {

		$(".play_cell.active").removeClass("active");

		this.setOptions({

			"score": 0

		});

		this.e.playScore.html(this.score);

	},

	//菜单

	menu: function () {

		var self = this;

		this.e_startBtn.click(function () {

			self.e_levelMenu.hide();

			if (self.playing) {

				self.pause();

			} else if (self.death) {

				self.resetArea();

				self.play();

			} else {

				self.play();

			}

		});

		this.e_levelBtn.click(function () {

			if (self.playing) return;

			self.e_levelMenu.toggle();

		});

		this.e_levelMenu.find("a").click(function () {

			self.e_levelMenu.hide();

			self.e_levelBtn.find(".level_text").html($(this).html());

			self.setOptions({

				"level": $(this).attr("level")

			});

		});

	},

	play: function () {

		var self = this;

		this.e_startBtn.html("暂停");

		this.playing = true;

		this.death = false;

		if (this.turning) {

			this.timer = setInterval(function () {

				self.offsetRow++;

				self.showTetris();

			}, this.interval[this.level]);

		} else {

			this.nextTetris();

		}

	},

	pause: function () {

		this.e_startBtn.html("开始");

		this.playing = false;

		clearTimeout(this.timer);

	},

	//初始化游戏

	init: function () {

		var self = this,
			_ele, _miniEle, _arr = [];

		//创建小方块放进cellArr里

		for (var i = 0; i < this.cellCol; ++i) {

			for (var j = 0; j < this.cellRow; ++j) {

				_ele = document.createElement("div");

				_ele.className = "play_cell";

				_ele.id = "play_cell_" + i + "_" + j;

				this.cellArr.push($(_ele));

				this.e_playArea.append(_ele);

			}

		}

		//创建下一个图形的小方块

		for (var m = 0; m < 16; ++m) {

			_miniEle = document.createElement("div");

			_miniEle.className = "play_mini_cell";

			this.miniCellArr.push($(_miniEle));

			this.e_nextType.append(_miniEle);

		}

		//保存三维数组tetrisArr的前两维数据编号，k为其第一维编号，j为其二维

		for (var k = 0, klen = this.tetrisArr.length; k < klen; ++k) {

			for (var j = 0, jlen = this.tetrisArr[k].length; j < jlen; ++j) {

				this.tetrisTypeArr.push([k, j]);

			}

		}

		//下一个图形的【x】【y】

		this.nextType = this.tetrisTypeArr[Math.floor(this.tetrisTypeArr.length * Math.random())];

		this.showNextType();

	},

	//按键控制函数

	control: function () {

		var self = this;

		$("html").keydown(function (e) {

			if (!self.playing)

				return !self.playing;

			switch (e.keyCode) {

				case 37:

					self.direction = "left";

					break;

				case 38:

					self.direction = "top";

					break;

				case 39:

					self.direction = "right";

					break;

				case 40:

					self.direction = "bottom";

					break;

				default:

					return;

					break;

			}

			//显示当前的按键操作

			self.e_playDirection.html(self.direction);

			//实行按键的操作函数

			self.drive();

			return false;

		});

	},

	//改变图形的状态

	changTetris: function () {

		var _len = this.tetrisArr[this.tetrisType[0]].length;

		if (this.tetrisType[1] < _len - 1) {

			this.tetrisType[1]++;

		} else {

			this.tetrisType[1] = 0;

		}

	},

	//按键的操作函数

	drive: function () {

		switch (this.direction) {

			case "left":

				if (this.offsetCol > 0) {

					this.offsetCol--;

				}

				break;

			case "top":

				this.changTetris();

				break;

			case "right":

				this.offsetCol++;

				break;

			case "bottom":

				if (this.offsetRow < this.cellRow - 2) {

					this.offsetRow++;

				}

				break;

			default:

				break;

		}

		//显示图形的函数

		this.showTetris(this.direction);

	},

	//显示图形的函数

	showTetris: function (dir) {

		var _tt = this.tetrisArr[this.tetrisType[0]][this.tetrisType[1]],
			_ele, self = this;

		this.turning = true;

		this.thisTetris = [];

		for (var i = _tt.length - 1; i >= 0; --i) {

			_ele = this.cellArr[_tt[i] + this.offsetCol + this.offsetRow * this.cellCol];

			//规定左右移动不能超出边框

			if (this.offsetCol < 7 && (_tt[i] + this.offsetCol + 1) % this.cellCol == 0) {

				this.offsetCol++;

				return;

			} else if (this.offsetCol > 7 && (_tt[i] + this.offsetCol) % this.cellCol == 0) {

				this.offsetCol--;

				return;

			}

			//判断是否下落是与方块碰撞了，排除向左移动时碰到左边的方块而停止下落

			if (_ele && _ele.hasClass("active") && dir == "left" && ($.inArray(_ele, this.preTetris) < 0)) {

				if (($.inArray(_ele, this.cellArr) - $.inArray(this.preTetris[i], this.cellArr)) % this.cellCol != 0) {

					this.offsetCol++;

					return;


				}

			}

			if (_ele && _ele.hasClass("active") && dir == "right" && ($.inArray(_ele, this.preTetris) < 0)) {

				if (($.inArray(_ele, this.cellArr) - $.inArray(this.preTetris[i], this.cellArr)) % this.cellCol != 0) {

					this.offsetCol--;

					return;

				}

			}

			//找到某个div进行下落的过程

			//否则找不到直接结束下落的过程

			if (_ele) {

				//找到一个div有class=active，而且不是上一次自身的位置，那么就停止

				//向下走了，因为遇到障碍物了

				//否则这个div符合，存起来

				if (_ele.hasClass("active") && ($.inArray(_ele, this.preTetris) < 0)) {

					this.tetrisDown();

					return;

				} else {

					this.thisTetris.push(_ele);

				}

			} else if (this.offsetRow > 0) {

				this.tetrisDown();

				return;

			}

		}

		//将上一次的位置的标记全部去除

		for (var j = 0, jlen = this.preTetris.length; j < jlen; ++j) {

			this.preTetris[j].removeClass("active");

		}

		//标记这一次的位置

		for (var k = 0, klen = this.thisTetris.length; k < klen; k++) {

			this.thisTetris[k].addClass("active");

		}

		//储存起现在的位置，会成为下一次的前一次位置

		this.preTetris = this.thisTetris.slice(0);

	},

	//无法下落时进行的函数

	tetrisDown: function () {

		clearInterval(this.timer);

		var _index;

		this.turning = false;

		//是否填充满一行的检测

		forOuter: for (var j = 0, jlen = this.preTetris.length; j < jlen; ++j) {

			_index = $.inArray(this.preTetris[j], this.cellArr);

			for (var k = _index - _index % this.cellCol, klen = _index - _index % this.cellCol + this.cellCol; k < klen; ++k) {

				if (!this.cellArr[k].hasClass("active")) {

					continue forOuter;

				}

			}

			if ($.inArray(_index - _index % this.cellCol, this.fullArr) < 0)

				this.fullArr.push(_index - _index % this.cellCol);

		}

		if (this.fullArr.length) {

			this.getScore();

			return;

		}

		//如果图形在6-9的div内被标记，那么游戏结束

		for (var i = 6; i < 9; ++i) {

			if (this.cellArr[i].hasClass("active")) {

				this.gameOver();

				alert("you lost!");

				return;

			}

		}

		this.nextTetris();

	},

	//下一个图形

	nextTetris: function () {

		var self = this;

		clearInterval(this.timer);

		this.preTetris = [];

		this.offsetRow = -2;

		this.offsetCol = 7;

		this.tetrisType = this.nextType;

		this.nextType = this.tetrisTypeArr[Math.floor(this.tetrisTypeArr.length * Math.random())];

		this.showNextType();

		this.timer = setInterval(function () {

			self.offsetRow++;

			self.showTetris();

		}, this.interval[this.level]);

	},

	showNextType: function () {

		//_nt为找到在this.nextType[0]行，this.nextType[1]列的一行数据

		var _nt = this.tetrisArr[this.nextType[0]][this.nextType[1]],
			_ele, _index;

		//找到class=active的元素，之后全部删除class元素

		this.e_nextType.find(".active").removeClass("active");

		for (var i = 0, ilen = _nt.length; i < ilen; ++i) {

			//因为offsetCol的最小值只能为2，所以在tetrisArr数组中赋的值都减了2

			//这里要加回2来处理，才能在小的视图里安放图形

			if (_nt[i] > this.cellCol - 2) {

				_index = (_nt[i] + 2) % this.cellCol - 1 + 4 * parseInt((_nt[i] + 2) / this.cellCol);

			} else {

				_index = _nt[i] + 1;

			}

			_ele = this.miniCellArr[_index];

			_ele.addClass("active");

		}

	},

	//获取分数

	getScore: function () {

		var self = this;

		//对完整的行消除，对元素进行向下移动

		for (var i = this.fullArr.length - 1; i >= 0; --i) {

			for (var j = 0; j < this.cellCol; ++j) {

				this.cellArr[j + this.fullArr[i]].removeClass("active");

				if (j == this.cellCol - 1) {

					for (var k = this.fullArr[i]; k >= 0; --k) {

						if (this.cellArr[k].hasClass("active")) {

							this.cellArr[k].removeClass("active");

							this.cellArr[k + this.cellCol].addClass("active");

						}

					}

				}

			}

		}

		this.score += this.levelScore[this.level] * this.doubleScore[this.fullArr.length - 1];

		this.e_playScore.html(this.score);

		this.fullArr = [];

		this.nextTetris();

	},

	//游戏结束

	gameOver: function () {

		this.death = true;

		this.pause();

		return;


	}

};

$(document).ready(function (e) {

	var t = new Tetris();

});
