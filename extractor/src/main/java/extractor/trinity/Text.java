package extractor.trinity;

/**
 * @author lisheng
 *         定义三叉树节点中的Text元素.
 */
public class Text implements DocDucument {

    private String text;//定义Text节点中的text字符串

    public Text(String text) {
        // TODO Auto-generated constructor stub
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return "Text{" +
                "text='" + text + '\'' +
                '}';
    }
}
