package com.udemyspringappdemo.demo.logic;

import com.udemyspringappdemo.demo.model.*;
import com.udemyspringappdemo.demo.model.projection.GroupReadModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

class ProjectServiceTest {

    @Test
    @DisplayName("should throw IllegalStateException when configured to allow just one group and the other undone group exist")
    void createGroup_noMultipleGroupsConfig_And_undoneGroupExists_throwsIllegalStateException() {

        //given
        var mockGroupRepository = groupRepositoryReturning(true);
        //and
        TaskConfigurationProperties mockConfig = configurationReturning(false);
        //system under test
        var toTest = new ProjectService(null, mockGroupRepository, mockConfig);
        //when
        var exception = catchThrowable(() -> toTest.createGroup(0, LocalDateTime.now()));
        //then

        assertThat(exception)
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("one undone group");
    }

    @Test
    @DisplayName("should throw IllegalStateException when configuration ok and no projects for a given id")
    void createGroup_ConfigurationOk_And_noProjects_throwsIllegalArgumentException() {

        //given
        var mockRepository = mock(ProjectRepository.class);
        when(mockRepository.findById(anyInt())).thenReturn(Optional.empty());
        //and
        TaskConfigurationProperties mockConfig = configurationReturning(true);
        //system under test
        var toTest = new ProjectService(mockRepository, null, mockConfig);
        //when
        var exception = catchThrowable(() -> toTest.createGroup(0, LocalDateTime.now()));
        //then

        assertThat(exception)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("id not found");
    }


    @Test
    @DisplayName("should throw IllegalStateException when configured to allow just one group and no  and projects for a given id")
    void createGroup_noMultipleGroupsConfig_And_undoneGroupsExists_noProjects_throwsIllegalArgumentException() {

        //given
        var mockRepository = mock(ProjectRepository.class);
        when(mockRepository.findById(anyInt())).thenReturn(Optional.empty());
        //and
        TaskGroupRepository taskGroupRepository = groupRepositoryReturning(false);
        //and
        TaskConfigurationProperties mockConfig = configurationReturning(true);
        //system under test
        var toTest = new ProjectService(mockRepository, taskGroupRepository, mockConfig);
        //when
        var exception = catchThrowable(() -> toTest.createGroup(0, LocalDateTime.now()));
        //then

        assertThat(exception)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("id not found");
    }


    @Test
    @DisplayName("should create a new group from project")
    void createGroup_configurationOk_existingProject_createsAndSavesGroup() {
        //given
        var today = LocalDate.now().atStartOfDay();
        var project = projectWith("bar",Set.of(-1,-2));
        var mockRepository = mock(ProjectRepository.class);

        when(mockRepository
                .findById(anyInt()))
                .thenReturn(Optional.of(project));
        //and
        TaskConfigurationProperties mockConfig = configurationReturning(true);
        //and
        TaskGroupRepository inMemoryGroupRepo = inMemoryGroupRepository();
        int countBeforeCall = inMemoryGroupRepository().count();
        //system under test
        var toTest = new ProjectService(mockRepository, inMemoryGroupRepo, mockConfig);

        //when
        GroupReadModel result = toTest.createGroup(1, today);
        //then
        assertThat(result.getDescription()).isEqualTo("bar");
        assertThat(result.getDeadline()).isEqualTo(today.minusDays(1));
        assertThat(result.getTasks()).allMatch(task -> task.getDescription().equals("bar"));
        assertThat(countBeforeCall).isEqualTo(inMemoryGroupRepository().count());

    }


        private Project projectWith(String projectDesc, Set<Integer> daysToDeadline ) {

        Set<ProjectStep> steps = daysToDeadline.stream()
                .map(days -> {
                    var step = mock(ProjectStep.class);
                    when(step.getDescription()).thenReturn("foo");
                    when(step.getDaysToDeadLine()).thenReturn(days);
                    return step;
                }).collect(Collectors.toSet());
        var result = mock(Project.class);

        when(result.getDescription()).thenReturn(projectDesc);
        when(result.getSteps()).thenReturn(steps);
        return result;
        }
        private TaskGroupRepository groupRepositoryReturning ( boolean result){
            var mockGroupRepository = mock(TaskGroupRepository.class);
            when(mockGroupRepository.existsByDoneIsFalseAndProject_Id(anyInt())).thenReturn(result);
            return mockGroupRepository;
        }
        private TaskConfigurationProperties configurationReturning ( boolean result){
            var mockTemplate = mock(TaskConfigurationProperties.Template.class);
            when(mockTemplate.isAllowMultipleTasks()).thenReturn(result);
            var mockConfig = mock(TaskConfigurationProperties.class);
            when(mockConfig.getTemplate()).thenReturn(mockTemplate);
            return mockConfig;
        }
        private InMemoryGroupRepository inMemoryGroupRepository () {
            return new InMemoryGroupRepository();
        }

    public static class InMemoryGroupRepository implements  TaskGroupRepository{

        public int count() {
            return map.values().size();
        }

        private Map<Integer, TaskGroup> map = new HashMap<>();
        private int index = 0;

        @Override
        public List<TaskGroup> findAll() {
            return new ArrayList<>(map.values());
        }

        @Override
        public Optional<TaskGroup> findById(Integer id) {
            return Optional.ofNullable(map.get(id));
        }

        @Override
        public boolean existsByDoneIsFalseAndProject_Id(Integer projectId) {
            return map.values().stream()
                    .filter(group ->!group.isDone())
                    .anyMatch(group->group.getProject() !=null && group.getProject().getId()==projectId);
        }

        @Override
        public TaskGroup save(TaskGroup entity) {

            if (entity.getId() == 0) {
                try {
                    var field =  TaskGroup.class.getDeclaredField("id");
                    field.setAccessible(true);
                    field.set(entity,++index);

                } catch (NoSuchFieldException | IllegalAccessException e) {
                    throw new RuntimeException();
                }
            }

            map.put(entity.getId(), entity);
            return entity;
        }

        @Override
        public List<Project> findAllByDoneIsFalse() {
            return null;
        }
    }

}

