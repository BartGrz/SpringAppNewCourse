package com.udemyspringappdemo.demo.model.projection;

import com.udemyspringappdemo.demo.model.Task;
import com.udemyspringappdemo.demo.model.TaskGroup;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Set;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;


class GroupReadModelTest {

     @Test
    @DisplayName("Should create null deadlines for group when no task deadline")
    void constructor_noDeadline_createsNullDeadline() {
        //given
        var source = new TaskGroup();
        source.setDescription("foo");
        source.setTasks(Set.of(new Task("bar",null)));

        //when
        var result = new GroupReadModel(source);
        //then
        assertThat(result).hasFieldOrPropertyWithValue("deadline",null);
    }

}