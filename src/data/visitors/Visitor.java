package data.visitors;

import data.AirDataCollector;
import data.source.IOnlineDataReader;

import java.io.IOException;

/**
 * Abstract class for visitors of {@link AirDataCollector}.
 */
public abstract class Visitor {
    /**
     * @param airDataCollector
     * @param args
     *         Parameters of the visitor.
     * @param dataSource
     *         Source for getting new data from.
     * @return
     */
    public abstract Object visit(AirDataCollector airDataCollector, String[] args, IOnlineDataReader dataSource) throws IOException;
}
