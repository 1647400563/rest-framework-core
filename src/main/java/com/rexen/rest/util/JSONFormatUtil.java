package com.rexen.rest.util;

/**
 * @author: GavinHacker
 * @description: json格式化工具
 * @modifiedBy:
 */
public final class JSONFormatUtil {

    /**
     * @author: GavinHacker
     * @description: json格式化主方法
     * @param: String json 未格式化的json
     * @return: 格式化之后的 json
     * @modifiedBy:
     */
    public static String format(String json) {
        if (null == json || "".equals(json)) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        char last = '\0';
        char current = '\0';
        int indent = 0;
        boolean isInQuotationMarks = false;
        for (int i = 0; i < json.length(); i++) {
            last = current;
            current = json.charAt(i);
            switch (current) {
                case '"':
                    if (last != '\\') {
                        isInQuotationMarks = !isInQuotationMarks;
                    }
                    sb.append(current);
                    break;
                case '{':
                case '[':
                    sb.append(current);
                    if (!isInQuotationMarks) {
                        sb.append('\n');
                        indent++;
                        appendTab(sb, indent);
                    }
                    break;
                case '}':
                case ']':
                    if (!isInQuotationMarks) {
                        sb.append('\n');
                        indent--;
                        appendTab(sb, indent);
                    }
                    sb.append(current);
                    break;
                case ',':
                    sb.append(current);
                    if (last != '\\' && !isInQuotationMarks) {
                        sb.append('\n');
                        appendTab(sb, indent);
                    }
                    break;
                default:
                    sb.append(current);
            }
        }

        return sb.toString();
    }

    /**
     * @author: GavinHacker
     * @description: 辅助方法
     * @modifiedBy:
     */
    private static void appendTab(StringBuilder sb, int indent) {
        for (int i = 0; i < indent; i++) {
            sb.append('\t');
        }
    }
}