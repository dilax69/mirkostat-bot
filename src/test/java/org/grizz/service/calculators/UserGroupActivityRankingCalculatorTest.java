package org.grizz.service.calculators;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.grizz.model.Entry;
import org.grizz.model.EntryComment;
import org.grizz.model.User;
import org.grizz.model.UserGroup;
import org.grizz.service.calculators.structures.RankedObject;
import org.junit.Test;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.junit.Assert.*;


@RunWith(MockitoJUnitRunner.class)
public class UserGroupActivityRankingCalculatorTest {
    private static final User USER_BLACK = User.builder().authorGroup(UserGroup.BLACK.getValue()).build();
    private static final User USER_BLUE = User.builder().authorGroup(UserGroup.BLUE.getValue()).build();

    private UserGroupActivityRankingCalculator calculator = new UserGroupActivityRankingCalculator();

    @Test
    public void consume() throws Exception {
        EntryComment firstComment = EntryComment.builder().authorGroup(UserGroup.BLACK.getValue()).voters(Lists.newArrayList()).build();
        EntryComment secondComment = EntryComment.builder().authorGroup(UserGroup.BLUE.getValue()).voters(Lists.newArrayList()).build();
        EntryComment thirdComment = EntryComment.builder().authorGroup(UserGroup.BLACK.getValue()).voters(Lists.newArrayList()).build();

        Entry entry = Entry.builder().authorGroup(UserGroup.BLACK.getValue()).voters(Lists.newArrayList(USER_BLACK, USER_BLUE))
                .comments(Lists.newArrayList(
                        thirdComment,
                        secondComment,
                        firstComment
                )).build();

        Entry entry2 = Entry.builder().authorGroup(UserGroup.GREEN.getValue()).voters(Lists.newArrayList(USER_BLUE))
                .comments(Lists.newArrayList()).build();

        calculator.consume(Sets.newHashSet(entry, entry2));
        List<RankedObject> value = (List<RankedObject>) calculator.getValue();

        assertThat(value, hasSize(3));
        assertThat(value.get(0), is(new RankedObject(UserGroup.BLACK, 4)));
        assertThat(value.get(1), is(new RankedObject(UserGroup.BLUE, 3)));
        assertThat(value.get(2), is(new RankedObject(UserGroup.GREEN, 1)));
    }

}