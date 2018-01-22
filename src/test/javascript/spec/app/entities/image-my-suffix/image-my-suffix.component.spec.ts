/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Rx';
import { Headers } from '@angular/http';

import { Hochzeitclick11TestModule } from '../../../test.module';
import { ImageMySuffixComponent } from '../../../../../../main/webapp/app/entities/image-my-suffix/image-my-suffix.component';
import { ImageMySuffixService } from '../../../../../../main/webapp/app/entities/image-my-suffix/image-my-suffix.service';
import { ImageMySuffix } from '../../../../../../main/webapp/app/entities/image-my-suffix/image-my-suffix.model';

describe('Component Tests', () => {

    describe('ImageMySuffix Management Component', () => {
        let comp: ImageMySuffixComponent;
        let fixture: ComponentFixture<ImageMySuffixComponent>;
        let service: ImageMySuffixService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [Hochzeitclick11TestModule],
                declarations: [ImageMySuffixComponent],
                providers: [
                    ImageMySuffixService
                ]
            })
            .overrideTemplate(ImageMySuffixComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ImageMySuffixComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ImageMySuffixService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new Headers();
                headers.append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of({
                    json: [new ImageMySuffix(123)],
                    headers
                }));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.images[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
