package com.udemyspringappdemo.demo.logic;


import com.udemyspringappdemo.demo.model.TaskGroup;
import com.udemyspringappdemo.demo.model.TaskGroupRepository;
import com.udemyspringappdemo.demo.model.TaskRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class TaskGroupServiceTest {


    @Test
    @DisplayName("should throw when undone tasks")
    void toogleGroup_undoneTasks_throwsIllegalStateEsception() {
        //given
        TaskRepository taskRepository = taskRepositoryReturning(true);
        //system under test
        var toTest = new TaskGroupService( null, taskRepository);
        var exception = catchThrowable(() -> toTest.toogleGroup(1));
        assertThat(exception).isInstanceOf(IllegalStateException.class).hasMessageContaining("Group has undone tasks. Done all the tasks first");
        //when
        //then
    }

    @Test
    @DisplayName("should throw when no group ")
    void toogleGroup_wrongId_throwsIllegalArgumentException() {
        //given
        TaskRepository mocktaskRepository = taskRepositoryReturning(false);
        //and
        var mockRepository = mock(TaskGroupRepository.class);
        when(mockRepository.findById(anyInt())).thenReturn(Optional.empty());
        //system under test
        var toTest = new TaskGroupService(mockRepository,mocktaskRepository);
        var exception = catchThrowable(() -> toTest.toogleGroup(1));
        assertThat(exception).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("TaskGroup with gven id not found");

    }

    @Test
    @DisplayName("should toogle group ")
    void toogleGroup_worksAsExpected() {
        //given
        TaskRepository mocktaskRepository = taskRepositoryReturning(false);
        //and
        var group = new TaskGroup();
        var beforeToogle = group.isDone();
        //and
        var mockRepository = mock(TaskGroupRepository.class);
        when(mockRepository.findById(anyInt())).thenReturn(Optional.of(group));
        //system under test
        var toTest = new TaskGroupService(mockRepository,mocktaskRepository);
       toTest.toogleGroup(0);
       assertThat(group.isDone()).isEqualTo(!beforeToogle);
    }

    private TaskRepository taskRepositoryReturning(boolean result) {
        TaskRepository taskRepository = mock(TaskRepository.class);
        when(taskRepository.existsByDoneIsFalseAndGroup_Id(anyInt())).thenReturn(result);
        return taskRepository;
    }
}