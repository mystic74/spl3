/**
 * 
 */
package bgu.spl.net.api;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;

/**
 * @author tom
 *
 */
public class bguFieldStringList extends bguField {

	bguFieldUserName[] m_userNameList = null;
	
	public bguFieldUserName[] get_userNameList() {
		return m_userNameList;
	}


	public bguFieldStringList() {
		// TODO Auto-generated constructor stub
	}
	
	
	public byte[] toByteArray()
	{
		ByteArrayOutputStream returnArray = new ByteArrayOutputStream();
		for (bguFieldUserName bguFieldUserName : m_userNameList) {
			try {
				returnArray.write(bguFieldUserName.getUsername().getBytes());
				returnArray.write('\0');
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return returnArray.toByteArray();
	}

	@Override
	public String toString() {
		String strConcatString = "";
		for (bguFieldUserName bguFieldUserName : m_userNameList) {
			strConcatString += bguFieldUserName.getUsername();
			strConcatString += (char)0;
		}
		
		return strConcatString;
		
	}



	public bguFieldStringList(Integer amountOfStrings) {
		m_userNameList = new bguFieldUserName[amountOfStrings];
	}
	
	public bguFieldStringList decode(byte nextByte, Integer amountOfStrings) {
		if (this.m_userNameList == null)
		{
			this.m_userNameList = new bguFieldUserName[amountOfStrings];
			for (int i = 0; i < amountOfStrings; i++)
			{
				this.m_userNameList[i] = new bguFieldUserName();
			}
		}
	
		for (bguFieldUserName bguFieldUserName : m_userNameList) {
			if (!bguFieldUserName.isDone())
			{
				bguFieldUserName.decode(nextByte);
				return null;
			}
		}
		this.isDone = true;
		return this;
	}

}
