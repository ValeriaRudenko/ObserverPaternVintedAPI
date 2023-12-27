package es.ull.patrones.strategy;

import java.util.List;
import java.util.Objects;

public interface JSONParserStrategy<T> {
    void parse(String data, Object... parameters);
}
