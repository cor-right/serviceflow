# serviceflow

## 项目结构

* serviceflow
	* src
		* ExceptionHandler 异常处理器，需要用户实现后进行注入
		* ServiceUnit 服务单元，需要用户实现后作为列表进行注入
		* ServiceFlowStarter 服务启动器，代码中调用 apply 方法启动引擎
			* SpringServiceFlowStarter spring环境下的启动器实现
	* target
		* serviceflow-1.0-SNAPSHOT.jar 直接使用该jar包
