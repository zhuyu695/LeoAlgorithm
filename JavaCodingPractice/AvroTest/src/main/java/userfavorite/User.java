package userfavorite;

import java.io.File;
import java.io.IOException;

import org.apache.avro.Schema;

public class User extends org.apache.avro.specific.SpecificRecordBase implements org.apache.avro.specific.SpecificRecord {
	public static final org.apache.avro.Schema SCHEMA$ = schema();
	
	public String name;
	public int favorite_number;
	public String favorite_color;
	
	public void setName(String sname) {
		name = sname;
	}
	
	public void setColor(String color) {
		favorite_color = color;
	}
	
	public void setNum(int num) {
		favorite_number = num;
	}
	
	public String getName() {
		return name;
	}
	
	public String getColor() {
		return favorite_color;
	}
	
	public int getNum() {
		return favorite_number;
	}

	@Override
	public Schema getSchema() {
		try {
			return new org.apache.avro.Schema.Parser().parse(new File("src/main/resources/User.avsc"));
		} catch (IOException e) {
			return null;
		}
	}
	
	public static Schema schema() {
		try {
			return new org.apache.avro.Schema.Parser().parse(new File("src/main/resources/User.avsc"));
		} catch (IOException e) {
			return null;
		}
	}

	@Override
	public Object get(int field) {
		switch (field) {
		    case 0: return name;
		    case 1: return favorite_number;
		    case 2: return favorite_color;
		    default: throw new org.apache.avro.AvroRuntimeException("Bad index");
	    }
	}

	@Override
	public void put(int field, Object value) {
		switch (field) {
		    case 0: name = value.toString(); return;
		    case 1: favorite_number = Integer.parseInt(value.toString()); return;
		    case 2: favorite_color = value.toString(); return;
		    default: throw new org.apache.avro.AvroRuntimeException("Bad index");
		}
    }
}
