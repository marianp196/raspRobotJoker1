/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package applicationBuilder;

import component.IComponent;
import dataSource.IDataSource;
import factory.IStepMotorFactory;
import factory.StepMotorFactory;
import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author marian
 */
public class ServoDataSource implements IDataSource{

    public ServoDataSource(IStepMotorFactory stepMotorFactory) {
        this._stepMotorFactory = stepMotorFactory;
    }

    
    
    @Override
    public Collection<IComponent> Read() {
        ArrayList<IComponent> comps = new ArrayList<>();
        
        for(int i = 0; i < 6; i++){
            try {
                comps.add((IComponent) _stepMotorFactory.Build(i));
            } catch (Exception ex) {
                Logger.getLogger(ServoDataSource.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return comps;
    }
    
    private IStepMotorFactory _stepMotorFactory;    
}
