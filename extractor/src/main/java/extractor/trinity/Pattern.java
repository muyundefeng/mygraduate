package extractor.trinity;

/**
 * @author lisheng
 * 定义html之间共享的模板
 */
public class Pattern implements DocDucument {

	public String string;

	public Pattern(){
		
	}
	public Pattern(String string) {
		super();
		this.string = string;
	}

	public String getString() {
		return string;
	}

	public void setString(String string) {
		this.string = string;
	}
	
}
