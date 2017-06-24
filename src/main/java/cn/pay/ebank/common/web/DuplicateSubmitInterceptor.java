package cn.pay.ebank.common.web;

import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * 
 * 解决重复提交有三种方式：<br/>
 * 1.在用户点击提交按钮后，用javascript禁用提交按钮，这样可以防止使用慢速网络的用户多次提交<br/>
 * 2.处理完表单后，重定向到新页面(<a
 * href="http://en.wikipedia.org/wiki/Post/Redirect/Get">PRG<
 * /a>)。这样可以防止用户在结果页面刷新导致重复提交。<br/>
 * 3.使用 <a
 * href="http://www.corej2eepatterns.com/Design/PresoDesign.htm">Synchronizer
 * Token</a> 防止重复提交<br/>
 * 
 * 
 * <p>
 * 本HandlerInterceptor用第三种方式解决此问题：
 * </p>
 * <p>
 * 用户请求页面时，生成一个唯一的token，token存储在session和页面的form hidden表单中。 当表单提交时，比较form
 * hidden里面的token和session中的token，如果不相等，则是重复提交，返回给客户端错误。
 * 如果token相等，则生成一个新的token放在session中。
 * </p>
 * 
 * <h3>Usage Examples</h3>
 * 
 * <li>1.配置handler</li>
 * <p>
 * 在spring servlet配置文件中加入拦截器配置
 * 
 * <pre>
 * 	{@code
 *    <mvc:interceptors>
 *               <bean id="duplicateSubmitInterceptor" class="com.yjf.common.web.DuplicateSubmitInterceptor" />
 *       </mvc:interceptors>
 *   }
 *	</pre>
 * </p>
 * 
 * <li>2.确认session中的参数是否暴露到request中</li>
 * <p>
 * org.springframework.web.servlet.view.velocity.VelocityLayoutViewResolver
 * bean配置时设置exposeSessionAttributes为true，此设置会把session中的参数复制到modelmap中 参考
 * {@link org.springframework.web.servlet.view.AbstractTemplateView#renderMergedOutputModel}
 * </p>
 * 
 * <li>3.页面使用</li>
 * <p>
 * vm模板中form表单里加入
 * 
 * <pre>
 *  {@code
 *   <input type="hidden" name="_synchronizerToken" value="$_synchronizerToken" />
 *    }
 * 	</pre>
 * </p>
 * 
 * <h3>注意事项：</h3> 此拦截器检测到重复提交后，会抛出异常，最好使用HandlerExceptionResolver来统一处理异常。
 * @Filename DuplicateSubmitHandler.java
 * 
 * @Description
 * 
 * @Version 1.0
 * 
 * @Author
 * 
 *
 *
 */
public class DuplicateSubmitInterceptor extends HandlerInterceptorAdapter {
	
	/**
	 * token key
	 */
	public static String TOKEN_KEY = "_synchronizerToken";
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		HttpSession session = request.getSession();
		if (session != null) {
			String token = (String) session.getAttribute(TOKEN_KEY);
			if (token == null) {
				session.setAttribute(TOKEN_KEY, nextToken());
				return true;
			} else {
				String tokenIn = request.getParameter(TOKEN_KEY);
				if (tokenIn == null) {
					return true;
				}
				if (tokenIn.equals(token)) {
					session.setAttribute(TOKEN_KEY, nextToken());
					return true;
				} else {
					DuplicateSubmitException dse = new DuplicateSubmitException("重复提交请求");
					dse.setReferer(request.getHeader("Referer"));
					dse.setRequestUri(request.getRequestURI());
					throw dse;
				}
			}
		} else {
			return true;
		}
		
	}
	
	private String nextToken() {
		long seed = System.currentTimeMillis();
		Random r = new Random();
		r.setSeed(seed);
		return Long.toString(seed) + Long.toString(r.nextLong());
	}
}
