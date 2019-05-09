package cn.thecutestgirl.keeper.model;

import java.util.List;
import javax.xml.bind.annotation.*;


/**
 * Helper class to wrap a list of accounts. This is used to save the list of accounts to XML;
 * @author minstrel
 *
 */
@XmlRootElement(name="passwordBook")
public class AccountListWrapper {
	
	private List<Account> accounts;
	
	public List<Account> getAccounts() {
		return accounts;
	}
	
	@XmlElement(name="account")
	public void setAccounts(List<Account> accounts) {
		this.accounts=accounts;
	}
	
}
