package fr.inria.lille.shexjava.util;

import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

import org.apache.commons.rdf.api.BlankNode;

public class MyBlankNodeImpl implements BlankNode {
	private static final UUID SALT = UUID.randomUUID();
	private static final AtomicLong COUNTER = new AtomicLong();

	private final String uniqueReference;
	
	public MyBlankNodeImpl() {
        this(SALT, Long.toString(COUNTER.incrementAndGet()));
    }
	
	public MyBlankNodeImpl(String name) {
		uniqueReference = name;
	}

    public MyBlankNodeImpl(final UUID uuidSalt, final String name) {
        if (Objects.requireNonNull(name).isEmpty()) {
            throw new IllegalArgumentException("Invalid blank node id: " + name);
        }

        final String uuidInput = "urn:uuid:" + uuidSalt + "#" + name;

        this.uniqueReference = UUID.nameUUIDFromBytes(uuidInput.getBytes(StandardCharsets.UTF_8)).toString();
    }

    @Override
    public String uniqueReference() {
        return uniqueReference;
    }

    @Override
    public String ntriplesString() {
        return "_:" + uniqueReference;
    }

    @Override
    public String toString() {
        return ntriplesString();
    }

    @Override
    public int hashCode() {
        return uniqueReference.hashCode();
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        // We don't support equality with other implementations
        if (!(obj instanceof MyBlankNodeImpl)) {
            return false;
        }
        final MyBlankNodeImpl other = (MyBlankNodeImpl) obj;
        if (uniqueReference == null) {
            if (other.uniqueReference != null) {
                return false;
            }
        } else if (!uniqueReference.equals(other.uniqueReference)) {
            return false;
        }
        return true;
    }

}