/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package applicationBuilder;

import factory.ComponentProviderFactory;
import factory.IBusI2CFactory;
import factory.IComponentProviderFactory;
import factory.IPvmBoardFactory;
import factory.IStepMotorFactory;
import factory.PvmBoardFactory;
import factory.StepMotorFactory;
import factory.BusI2CFactory;
import services.IComponentProvider;

/**
 *
 * @author marian
 */
public class ApplicationBuilder {
    public static IComponentProvider Build() throws Exception{
                
        if(_instance == null)            
            buildInstance();            
        
        return _instance;
    }

    private static void buildInstance() throws Exception {
        //irgendwas ist hier noch nicht ganz richtig-... I2C Adresse wir komisch übergeben 
        IBusI2CFactory i2CFactory = new BusI2CFactory();
        IPvmBoardFactory pvmBoardFactory = new PvmBoardFactory(i2CFactory, 0x40);
        IStepMotorFactory stepmotorFactory = new StepMotorFactory(pvmBoardFactory, 0x40);
        
        IComponentProviderFactory factory = new ComponentProviderFactory();
        _instance = factory.Build(new DataSourceProvider(stepmotorFactory));
    }
    
    private static IComponentProvider _instance;
}
