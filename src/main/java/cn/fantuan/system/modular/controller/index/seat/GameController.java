package cn.fantuan.system.modular.controller.index.seat;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/seat/")
public class GameController {
	private final String PATH = "/subsidiary/page/";

	//休闲娱乐的视图控制器
	@RequestMapping("game")
	public String sign() {
		return PATH + "GAME-Tetris";
	}
}