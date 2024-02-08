package com.example.javafxinterpretor.repository;


import com.example.javafxinterpretor.model.InterpretorException;
import com.example.javafxinterpretor.model.ProgramState;

import java.util.List;

public interface Repository {
    public List<ProgramState> getProgramList();

    public void setProgramList(List<ProgramState> newList);

    public void add(ProgramState state);

    public void logProgramState(ProgramState stateToLog) throws InterpretorException;
}
