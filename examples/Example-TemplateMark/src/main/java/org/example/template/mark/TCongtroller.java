package org.example.template.mark;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fast.dev.core.model.InvokerResult;

@Controller
@RequestMapping
public class TCongtroller {

	@RequestMapping("ftl")
	public Object ftl() {
		return "test.ftl";
	}

	@RequestMapping("gtl")
	public Object gtl() {
		return "test.gtl";
	}

	@RequestMapping("vtl")
	public Object vtl() {
		return "test.vtl";
	}

	@RequestMapping("htl")
	public Object htl() {
		return "test.htl";
	}

	@RequestMapping("jsp")
	public Object jsp() {
		return "test";
	}

	@RequestMapping("test.json")
	public InvokerResult<Object> test() {
		return new InvokerResult<Object>(System.currentTimeMillis());
	}

}
