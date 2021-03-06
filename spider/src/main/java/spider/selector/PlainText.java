package spider.selector;

import java.util.ArrayList;
import java.util.List;


public class PlainText extends AbstractSelectable {

    protected List<String> sourceTexts;

    public PlainText(List<String> sourceTexts) {
        this.sourceTexts = sourceTexts;
    }

    public PlainText(String text) {
        this.sourceTexts = new ArrayList<String>();
        sourceTexts.add(text);
    }

    public static PlainText create(String text) {
        return new PlainText(text);
    }

    //@Override
    public Selectable xpath(String xpath) {
        throw new UnsupportedOperationException();
    }

    //@Override
    public Selectable $(String selector) {
        throw new UnsupportedOperationException();
    }

    //@Override
    public Selectable $(String selector, String attrName) {
        throw new UnsupportedOperationException();
    }

    //@Override
    public Selectable smartContent() {
        throw new UnsupportedOperationException();
    }

    //@Override
    public Selectable links() {
        throw new UnsupportedOperationException();
    }

    //@Override
    public List<Selectable> nodes() {
        List<Selectable> nodes = new ArrayList<Selectable>(getSourceTexts().size());
        for (String string : getSourceTexts()) {
            nodes.add(PlainText.create(string));
        }
        return nodes;
    }

    @Override
    protected List<String> getSourceTexts() {
        return sourceTexts;
    }
}
