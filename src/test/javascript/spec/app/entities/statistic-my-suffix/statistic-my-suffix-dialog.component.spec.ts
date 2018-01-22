/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { Hochzeitclick11TestModule } from '../../../test.module';
import { StatisticMySuffixDialogComponent } from '../../../../../../main/webapp/app/entities/statistic-my-suffix/statistic-my-suffix-dialog.component';
import { StatisticMySuffixService } from '../../../../../../main/webapp/app/entities/statistic-my-suffix/statistic-my-suffix.service';
import { StatisticMySuffix } from '../../../../../../main/webapp/app/entities/statistic-my-suffix/statistic-my-suffix.model';
import { ProfileMySuffixService } from '../../../../../../main/webapp/app/entities/profile-my-suffix';

describe('Component Tests', () => {

    describe('StatisticMySuffix Management Dialog Component', () => {
        let comp: StatisticMySuffixDialogComponent;
        let fixture: ComponentFixture<StatisticMySuffixDialogComponent>;
        let service: StatisticMySuffixService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [Hochzeitclick11TestModule],
                declarations: [StatisticMySuffixDialogComponent],
                providers: [
                    ProfileMySuffixService,
                    StatisticMySuffixService
                ]
            })
            .overrideTemplate(StatisticMySuffixDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(StatisticMySuffixDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(StatisticMySuffixService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new StatisticMySuffix(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(entity));
                        comp.statistic = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'statisticListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new StatisticMySuffix();
                        spyOn(service, 'create').and.returnValue(Observable.of(entity));
                        comp.statistic = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'statisticListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
