package org.ypf.generic.orm.entityoper.defineimpl;


import org.ypf.generic.orm.entityoper.*;

import java.util.ArrayList;
import java.util.List;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * @date: 2022/6/1 15:48
 */
public class EntityMapperImpl implements EntityMapper {
    Table table;
    JavaClass javaClass;
    List<ColumFieldBinding> cFBindings = new ArrayList<>();
    List<ColumFieldBinding> exclusivePrimay = new ArrayList<>();
    ColumFieldBinding primary;
    String entityMapperName;
    private AtomicInteger version_;

    public AtomicInteger getVersion_() {
        return version_;
    }

    public EntityMapperImpl(Table table, JavaClass javaClass) {
        this.table = table;
        this.javaClass = javaClass;
        bind();


    }


    @Override
    public EntityMapper mirror() {

        return new EntityMapperImpl(table.mirror(), javaClass.mirror());
    }


    @Override
    public Table table() {
        return table;
    }

    @Override
    public JavaClass javaClass() {
        return javaClass;
    }

    @Override
    public List<ColumFieldBinding> cFBindings() {
        return cFBindings.stream().filter(e -> !e.isSuspend()).collect(Collectors.toList());
    }

    @Override
    public List<ColumFieldBinding> exclusivePrimay() {
        return exclusivePrimay.stream().filter(e -> !e.isSuspend()).collect(Collectors.toList());
    }

    @Override
    public ColumFieldBinding primay() {
        return primary;
    }

    @Override
    public ColumFieldBinding getColumFieldBinding(String name) {

        return cFBindings.stream().filter(e -> e.getName().equals(name)).findFirst().get();

    }

    @Override
    public boolean hasColumFieldBinding(String name) {
        return cFBindings.stream().filter(e -> e.getName().equals(name)).findFirst().isPresent();
    }

    @Override
    public String getName() {
        return entityMapperName;
    }

    void bind() {
        List<Column> columnList = table.column();
        for (Column column : columnList) {
            String bk = column.bindKey();
            javaClass.fields().stream()
                    .filter(e -> e.bindKey().equals(bk))
                    .findFirst()
                    .ifPresent(f -> {
                        CFBindingImpl cfBinding = new CFBindingImpl(bk, column, f);
                        cfBinding.setEntityMapper(this);
                        cFBindings.add(cfBinding);
                        if (column.isPrimaryId()) {
                            primary = cfBinding;
                        } else {
                            exclusivePrimay.add(cfBinding);
                        }
                    });

        }


    }

}
