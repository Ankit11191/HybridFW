package validators;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class FindNotConnected {

	public static void main(String[] args) {
		File file=new File("C:\\Eclips\\business\\log\\Application1_2018_05_24.log");
		String line = "";
		BufferedReader reader = null;

		try {
			reader = new BufferedReader(new FileReader(file));

			while ((line = reader.readLine()) != null) {
				if(line.contains("----------------------"))
				{
					try(BufferedWriter bw1 = new BufferedWriter(new FileWriter("C:\\Eclips\\all.txt",true)))
					{
						bw1.write(line+"\n");
					}
				}
				else if(line.contains(":404"))
				{
					try(BufferedWriter bw2 = new BufferedWriter(new FileWriter("C:\\Eclips\\404.txt",true)))
					{
						bw2.write(line+"\n");
					}					
				}
				else if(line.contains(":200"))
				{
					try(BufferedWriter bw3 = new BufferedWriter(new FileWriter("C:\\Eclips\\200.txt",true)))
					{
						bw3.write(line+"\n");
					}
				}
					
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
