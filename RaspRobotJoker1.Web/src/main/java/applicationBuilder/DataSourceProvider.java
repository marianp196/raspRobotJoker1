/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package applicationBuilder;

import dataSource.IDataSource;
import dataSource.IDataSourceProvider;
import factory.IStepMotorFactory;
import java.util.ArrayList;
import java.util.Collection;

/**
 *
 * @author marian
 */
public class DataSourceProvider implements IDataSourceProvider{

    public DataSourceProvider(IStepMotorFactory stepmotFactory) {
        this._stepmotFactory = stepmotFactory;
    }    
    
    @Override
    public Collection<IDataSource> Get() {
        ArrayList<IDataSource> list = new ArrayList<>();
        list.add(new ServoDataSource(_stepmotFactory));
        return list;
    }
    
    private IStepMotorFactory _stepmotFactory;
}
