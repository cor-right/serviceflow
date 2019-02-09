# serviceflow

## serviceflow/serviceflow

该项目是引擎本体，是java项目，使用时通过jar包 的方式进行使用。
目前引擎只提供与Spring 框架生命周期结合的使用方式，需要在 spring配置文件上进行配置。

* serviceflow
	* src
		* ExceptionHandler 异常处理器，需要用户实现后进行注入
		* ServiceUnit 服务单元，需要用户实现后作为列表进行注入
		* ServiceFlowStarter 服务启动器，代码中调用 apply 方法启动引擎
			* SpringServiceFlowStarter spring环境下的启动器实现
	* target
		* serviceflow-1.0-SNAPSHOT.jar 直接使用该jar包
