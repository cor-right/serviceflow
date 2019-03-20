# serviceflow


## 框架定位

依据用户编写的工作流定义，将服务进行编排和执行，主要目的是将繁杂、冗长的业务进行类级别的拆分。

## 框架特点
和现在已有的微服务工作流相比，该引擎的优势包括：

* 免配置：
    * 免xml：该引擎高度适配springBoot等开发工具，无需任何类型的配置文件，只需用户定义一个流程定义对象即可；
    * 免bean声明：工作流中的服务单元，都采取动态加载的模式，无需进行 @Service 等各种类型的 bean 显式声明；
    * 免scope声明：工作流引擎中的各个组件及服务单元，都采用固定的原型模式，用户无需也不允许进行其他类型的Scope声明。
* 低侵入：
    使用引擎不会对无关业务或链路产生任何影响
* 强领域约束：
    对领域模型进行强制约束，避免在编码中不小心依赖到外围二方、三方包的模型，提高安全性
* 高灵活度：
    工作流流程中的任何一个服务单元，都可以很方便的提取出来形成独立的服务。
* 配备实用工具：
    * 服务单元关系可视化工具

## 框架说明
> 用户使用时，代码参照 serviceflow_demo

### 组件说明

#### ServiceFlowStarter
引擎本体，serviceflow 目前提供了 `SpringServiceFlowStarterWithOrderManager` 实现，支持 `FlowOrderManager` 。

#### FlowOrderManager
流程定义对象，用途是定义一条工作流， `serviceUnitsOrderList` 方法的返回值，是对工作流的定义。<br/>
对 流程定义对象 本身是没有进行过多约束的，它甚至可以不是一个 bean，但，返回必须是 serviceUnit 实现类的全限定名构成的列表，且其中属性必须符合规则。
<br/>举个例子
```$xslt
/**
 * 支付工作流流程定义
 *
 * @return
 */
@Override
public List<String> serviceUnitsOrderList() {
    return Arrays.asList(
            "me.jiaxu.demo.biz.common.UserValidate",
            "me.jiaxu.demo.biz.common.SellerValidate",
            "me.jiaxu.demo.biz.common.ItemValidate",
            "me.jiaxu.demo.biz.payment.DiscountCalculate",
            "me.jiaxu.demo.biz.payment.PaymentCalculate",
            "me.jiaxu.demo.biz.payment.MoneyExamination",
            "me.jiaxu.demo.biz.payment.CreateOrder",
            "me.jiaxu.demo.biz.payment.RenderResponse"
    );
}
```


#### ServiceUnit
服务单元，也叫工作单元，是将复杂链路进行拆分后，原有逻辑的处理位置，逻辑通常写在 `process` 中，但引擎也提供了 `before` 和 `after` 的前置、后置处理功能。<br/>
服务单元，本身不需要进行任何类型的声明，只需要继承 serviceUnit 接口就可以定义出来，在 flowOrderManager 中进行定义即可参与业务，框架会自动进行 serviceUnit 的bean注册和初始化。<br/>
注意，由于 serviceUnit 本身是有状态且状态会变化，所以所有的 serviceUnit 都是原型模式的。


#### ExceptionHandler
用户业务异常处理器，框架本身的异常，会自己消化，这里给用户的业务逻辑异常，提供了接口。

### 使用方式

导入 serviceflow maven包
```$xslt
<dependency>
    <groupId>me.jiaxu</groupId>
    <artifactId>serviceflow</artifactId>
    <version>1.0.0.RELEASE</version>
</dependency>
```

定义工作流程（FlowOrderManager） 和 服务单元（ServiceUnit）

\[可选]设置异常处理器和领域模型校验规则
```$xslt
/**
 * 设置领域限定规则
 *
 * @param rule 规则
 */
void setDomainConstraintRule(String rule);

/**
 * 设定用户业务异常处理器
 *
 * @param exceptionHandler
 */
void setExceptionHandler(ExceptionHandler exceptionHandler);
```

执行引擎的启动方法 `apply` 
```$xslt
@Service
public class Payment implements FlowOrderManager {

    @Autowired
    private SpringServiceFlowStarterWithOrderManager<BaseRequest, BaseResponse<PaymentModel>> starter;

    /**
     * 支付工作流流程定义
     *
     * @return
     */
    @Override
    public List<String> serviceUnitsOrderList() {
        return Arrays.asList(
                "me.jiaxu.demo.biz.common.UserValidate",
                "me.jiaxu.demo.biz.common.SellerValidate",
                "me.jiaxu.demo.biz.common.ItemValidate",
                "me.jiaxu.demo.biz.payment.DiscountCalculate",
                "me.jiaxu.demo.biz.payment.PaymentCalculate",
                "me.jiaxu.demo.biz.payment.MoneyExamination",
                "me.jiaxu.demo.biz.payment.CreateOrder",
                "me.jiaxu.demo.biz.payment.RenderResponse"
        );
    }

    public BaseResponse<PaymentModel> apply(BaseRequest request) {
        try {
            return starter.apply(request, this);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


}
```

### 注解
serviceflow 为了简化配置，更好的融入当前 springboot、sofaboot 等开发工具，提供了注解开发的功能。
目前 serviceflow 提供了四类注解：

#### In
业务链路入参，没有约束

#### Out
业务链路出参，相当于 response，一条工作流中，只能由一个 Field 被标记为 @Out

#### Publish
发布属性， @Publish 和 @Subscribe 的关系类似于 Spring 的 @Service 和 @Autowried 的关系，都是发布和订阅。当 serviceUnit 内部的 Field 被标记为 @Publish，就会被引擎获取，并自动注入到 被标记了@Subscribe 且 属性名、属性类型都相同的其他 servceUnit 的 Field 中。

#### Subscribe
订阅属性，必须保证订阅的属性在工作流中已经被 @Publish