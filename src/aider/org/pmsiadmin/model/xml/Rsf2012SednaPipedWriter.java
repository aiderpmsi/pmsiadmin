package aider.org.pmsiadmin.model.xml;

import aider.org.pmsi.dto.Rsf2012PipedWriter;
import aider.org.pmsi.parser.PmsiRSF2012Reader;
import aider.org.pmsi.parser.exceptions.PmsiPipedIOException;

/**
 * Objet de transfert de données pour un objet de type {@link PmsiRSF2012Reader}
 * @author delabre
 *
 */
public class Rsf2012SednaPipedWriter extends Rsf2012PipedWriter {

	PmsiSednaPipedReader reader;
	
	/**
	 * Construction de la connexion à la base de données à partir des configurations
	 * données
	 * @throws PmsiPipedIOException 
	 */
	public Rsf2012SednaPipedWriter(PmsiSednaPipedReader reader) throws PmsiPipedIOException {
		super(reader);
		this.reader = reader;
	}
	
	@Override
	public void writeStartDocument(String name, String[] attributes, String[] values) throws PmsiPipedIOException {
		String[] newAttributes = new String[attributes.length + 1];
		String[] newValues = new String[attributes.length + 1];
		System.arraycopy(attributes, 0, newAttributes, 0, attributes.length);
		System.arraycopy(values, 0, newValues, 0, attributes.length);
		
		newAttributes[attributes.length + 1] = "insertionTimeStamp";
		newValues[attributes.length + 1] = reader.getSednaTime();
		super.writeStartDocument(name, attributes, values);
	};
}