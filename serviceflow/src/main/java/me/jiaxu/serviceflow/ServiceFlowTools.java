package me.jiaxu.serviceflow;

import lombok.Data;
import me.jiaxu.serviceflow.common.util.CollectionUtils;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Stream;

/**
 * Created by jiaxu.zjx on 2019/3/19
 * Description:
 *     提供的工具
 */
@Data
@Service
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class ServiceFlowTools {

    /** flowOrderManager */
    private List<FlowOrderManager> flowManagerList = new ArrayList<>();

    /** flowOrderManager - serviceUnitName */
    private Map<String, List<ServiceUnit>> serviceUnitMap    = new HashMap<>();

    /** serviceUnitName - List of fieldname */
    private Map<ServiceUnit, List<String>> inMap      = new HashMap<>();

    /** serviceUnitName - List of fieldname */
    private Map<ServiceUnit, List<String>> outMap     = new HashMap<>();

    /** serviceUnitName - List of fieldname */
    private Map<ServiceUnit, List<String>> subscribeMap   = new HashMap<>();

    /** serviceUnitName - List of fieldname  */
    private Map<ServiceUnit, List<String>> publishMap     = new HashMap<>();

    /**
     * 获取 html 字符串
     */
    public String generateRelationMapInHTML() {

        String head =
                "<html>" +
                "<head>" +
                "    <meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />" +
                "    <title>Micro-Service Workflow Service Units\' Relation Map</title>" +
                "    <style type=\"text/css\">" +
                "            table, th , td  {" +
                "              border: 1px solid grey;" +
                "              font-family: Sitka Text;     " +
                "              border-collapse: collapse;" +
                "              padding: 5px;" +
                "            }" +
                "            table tr:nth-child(odd) {" +
                "              background-color: #f1f1f1;" +
                "            }" +
                "            table tr:nth-child(even) {" +
                "              background-color: #ffffff;" +
                "            }" +
                "    </style>" +
                "</head>" +
                "<body>";

        StringBuilder table = new StringBuilder();
        for (FlowOrderManager flow : flowManagerList) {
            String flowName = flow.getClass().getName();
            table.append("<table  width=\"80%\" align=\"center\"><tr style='background-color: #f1f1f1;s'>");
            // 添加表头
            table.append("<td colspan=\"5\"><b>FlowOrderManager: ")
                    .append(flowName)
                    .append("</b></td></tr>")
                    .append("    <tr style='background-color: #ffffff;'>")
                    .append("        <td><b>ServiceUnit</b></td>")
                    .append("        <td><b>In</b></td>")
                    .append("        <td><b>Subscribe</b></td>")
                    .append("        <td><b>Publish</b></td>")
                    .append("        <td><b>Out</b></td>")
                    .append("    </tr>")
                    .append("    <!--  -->");

            // 添加每个 serviceunit 的关联关系
            List<ServiceUnit> serviceUnitList = serviceUnitMap.get(flowName);
            int realRowNum = 0;

            for (ServiceUnit serviceUnit : serviceUnitList) {
                List<String> inList         = inMap.get(serviceUnit);
                List<String> outList        = outMap.get(serviceUnit);
                List<String> publishList    = publishMap.get(serviceUnit);
                List<String> subscribeList  = subscribeMap.get(serviceUnit);

                // 决定了当前 unit 会占用多少行
                int rowNum = Stream
                        .of(inList, outList, publishList, subscribeList)
                        .map(List::size)
                        .max(Integer::compareTo)
                        .get();

                for (int i = 0; i < rowNum; i++) {


                    String tr = ++realRowNum % 2 == 0?
                            "<tr onmouseover=\"this.style.backgroundColor='#ffff66'\"; onmouseout=\"this.style.backgroundColor='#f1f1f1';\">":
                            "<tr onmouseover=\"this.style.backgroundColor='#ffff66'\"; onmouseout=\"this.style.backgroundColor='#ffffff';\">";
                    table.append(tr);

                    if (i == 0) {
                        table
                                .append("<td>")
                                .append(serviceUnit.getClass().getName())
                                .append("</td>");

                        String in = CollectionUtils.getListElemSafely(inList, 0);
                        String out = CollectionUtils.getListElemSafely(outList, 0);
                        String subscribe = CollectionUtils.getListElemSafely(subscribeList, 0);
                        String publish = CollectionUtils.getListElemSafely(publishList, 0);

                        table
                                .append(in == null ?
                                        "<td style=\"border: 0px\"></td>" : "<td>" + in + "</td>")
                                .append(subscribe == null ?
                                        "<td style=\"border: 0px\"></td>" : "<td>" + subscribe + "</td>")
                                .append(publish == null ?
                                        "<td style=\"border: 0px\"></td>" : "<td>" + publish + "</td>")
                                .append(out == null ?
                                        "<td style=\"border: 0px\"></td>" : "<td>" + out + "</td>")
                                .append("</tr>");
                        continue;
                    }

                    String in = CollectionUtils.getListElemSafely(inList, i);
                    String out = CollectionUtils.getListElemSafely(outList, i);
                    String subscribe = CollectionUtils.getListElemSafely(subscribeList, i);
                    String publish = CollectionUtils.getListElemSafely(publishList, i);

                    table.append("<td style=\"border: 0px\"></td>");
                    table.append(in == null ?
                            "<td style=\"border: 0px\"></td>" : "<td>" + in + "</td>");
                    table.append(subscribe == null ?
                            "<td style=\"border: 0px\"></td>" : "<td>" + subscribe + "</td>");
                    table.append(publish == null ?
                            "<td style=\"border: 0px\"></td>" : "<td>" + publish + "</td>");
                    table.append(out == null ?
                            "<td style=\"border: 0px\"></td>" : "<td>" + out + "</td>");
                    table.append("</tr>");
                }
            }
            table.append("</table><br/>");
        }
        String bottom = "</body></html>";

        return head + table.toString() + bottom;
    }

}
