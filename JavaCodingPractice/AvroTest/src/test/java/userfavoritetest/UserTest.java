package userfavoritetest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import org.apache.avro.Schema;
import org.apache.avro.Schema.Parser;
import org.apache.avro.generic.GenericData;
import org.apache.avro.generic.GenericDatumReader;
import org.apache.avro.generic.GenericDatumWriter;
import org.apache.avro.generic.GenericRecord;
import org.apache.avro.io.DatumReader;
import org.apache.avro.io.DatumWriter;
import org.apache.avro.io.Decoder;
import org.apache.avro.io.DecoderFactory;
import org.apache.avro.io.Encoder;
import org.apache.avro.io.EncoderFactory;
import org.apache.avro.specific.SpecificDatumReader;
import org.apache.avro.specific.SpecificDatumWriter;
import org.apache.avro.util.Utf8;
import org.junit.Before;
import org.junit.Test;

import userfavorite.User;

public class UserTest {
	String avscPath = "src/main/resources/User.avsc";
	Parser schemaParser = new Schema.Parser();
	Schema schema;
	
	@Before
	public void setUp() throws IOException {
	schema = schemaParser.parse(new File(avscPath));
	}
	
	@Test
	public void prettifySchema() throws IOException {
	System.out.println(schema.toString(true));
	}
	
	@Test
	public void createGenericRecordAndSerializeThenDeserialize()
		throws IOException {
		// given
		GenericRecord datum = new GenericData.Record(schema);
		datum.put("name", new Utf8("Aeo"));
		datum.put("favorite_color", new Utf8("Red"));
		datum.put("favorite_number", 6);
		
		// serialize
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		DatumWriter<GenericRecord> writer = new GenericDatumWriter<GenericRecord>(
		schema);
		Encoder encoder = EncoderFactory.get().binaryEncoder(out, null);
		writer.write(datum, encoder);
		encoder.flush();
		out.close();
		
		// deserialize
		DatumReader<GenericRecord> reader = new GenericDatumReader<GenericRecord>(
		schema);
		Decoder decoder = DecoderFactory.get().binaryDecoder(out.toByteArray(),
		null);
		GenericRecord result = reader.read(null, decoder);
		
		assertEquals(result.get("name").toString(), "Aeo");
		assertEquals(result.get("favorite_number"), 6);
	}
	
	@Test
	public void createPairAndSerializeThenDeserialize() throws IOException {
		// given
		User datum = new User();
		datum.setName("Val");
		datum.setColor("Green");
		datum.setNum(8);
		
		// serialize
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		DatumWriter<User> writer = new SpecificDatumWriter<User>(User.class);
		Encoder encoder = EncoderFactory.get().binaryEncoder(out, null);
		writer.write(datum, encoder);
		encoder.flush();
		out.close();
		
		// deserialize
		DatumReader<User> reader = new SpecificDatumReader<User>(User.class);
		Decoder decoder = DecoderFactory.get().binaryDecoder(out.toByteArray(),
		null);
		User result = reader.read(null, decoder);
		
		assertEquals(result.getName(), "Val");
		assertEquals(result.getNum(), 8);
	}

}
