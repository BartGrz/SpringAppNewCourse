package com.udemyspringappdemo.demo.logic;

import com.udemyspringappdemo.demo.model.TaskConfigurationProperties;
import com.udemyspringappdemo.demo.model.TaskGroupRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.InstanceOfAssertFactories.LOCAL_DATE_TIME;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

class ProjectServiceTest {

    @Test
    @DisplayName("should throw IllegalStateException when configured to allow just one group and the other undone group exist")
    void createGroup_noMultipleGroupsConfig_And_undoneGroupExists_throwsIllegalStateException() {

        //given
        var mockGroupRepository = mock(TaskGroupRepository.class);
        when(mockGroupRepository.existsByDoneIsFalseAndProject_Id(anyInt())).thenReturn(true);
        //and
        var mockTemplate =mock(TaskConfigurationProperties.Template.class);
        when(mockTemplate.isAllowMultipleTasks()).thenReturn(false);
        //and
        var mockConfig = mock(TaskConfigurationProperties.class);
        when(mockConfig.getTemplate()).thenReturn(mockTemplate);
        //system under test
        var toTest = new ProjectService(null,mockGroupRepository,mockConfig);
        //when
        toTest.createGroup(0, LocalDateTime.now());
        var exception = catchThrowable(()->toTest.createGroup(0,LocalDateTime.now()));
        //then

        assertThat(exception)
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("one undone group");
        }


  /*
        assertThatExceptionOfType(IllegalStateException.class).isThrownBy(()->
           toTest.createGroup(0,LocalDateTime.now())).isInstanceOf(IllegalStateException.class);
         */


    }
