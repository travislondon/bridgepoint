package org.xtuml.bp.io.xmi.translate.processors.sql;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.sdmetrics.model.ModelElement;

import org.xtuml.bp.io.xmi.translate.processors.IdProcessor;
import org.xtuml.bp.io.xmi.translate.processors.generated.AbstractReferredToClassInAssocProcessor;

public class ReferredToClassInAssocProcessorSQL extends AbstractReferredToClassInAssocProcessor {

    private String classId = IdProcessor.NULL_ID;
    private String assocId = IdProcessor.NULL_ID;
    private String classInAssocId = IdProcessor.NULL_ID;

    public ReferredToClassInAssocProcessorSQL(String classId, String assocId, String classInAssocId) {
        this.classId = classId;
        this.assocId = assocId;
        this.classInAssocId = classInAssocId;
    }

    @Override
    public String getObj_ID() {
        return SQLUtils.idValue(classId);
    }

    @Override
    public String getRel_ID() {
        return SQLUtils.idValue(assocId);
    }

    @Override
    public String getOIR_ID() {
        return SQLUtils.idValue(classInAssocId);
    }

    @Override
    public String getOid_ID() {
        return SQLUtils.numberValue(-1);
    }

    @Override
    public String getProcessorOutput() {
        return SQLUtils.getProcessorOutput(this);
    }

    @Override
    public List<String> getValues(ModelElement element) {
        return Stream.of(getObj_ID(), getRel_ID(), getOIR_ID(), getOid_ID()).collect(Collectors.toList());
    }
}